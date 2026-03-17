package com.hrafty.web_app.services.ServicesImpl;

import com.hrafty.web_app.Repository.*;
import com.hrafty.web_app.Security.jwt.JwtService;
import com.hrafty.web_app.config.JwtConfig;
import com.hrafty.web_app.dto.request.*;
import com.hrafty.web_app.dto.response.*;
import com.hrafty.web_app.entities.*;
import com.hrafty.web_app.entities.enums.AccountStatus;
import com.hrafty.web_app.entities.enums.Role;
import com.hrafty.web_app.exception.*;
import com.hrafty.web_app.mapper.UserMapper;
import com.hrafty.web_app.services.AuthService;
import com.hrafty.web_app.services.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

/*
 * Fixes applied vs. the previous version:
 *
 * FIX 1: Removed "import org.apache.coyote.BadRequestException"
 *   That is a Tomcat-internal class, not our custom exception.
 *   All methods now use our own exception classes from com.hrafty.web_app.exception.
 *
 * FIX 2: Removed "@Value("${app.jwt.refresh-token-expiration}")"
 *   That property key doesn't exist. Our JwtConfig uses "security.jwt.*" prefix.
 *   Replaced with direct injection of JwtConfig to read the expiration value.
 *
 * FIX 3: Fixed inconsistent exception names.
 *   Old code used EmailAlreadyExistsException, InvalidRequest — neither existed.
 *   Now using DuplicateResourceException and BadRequestException consistently.
 *
 * FIX 4: Wired EmailService.
 *   Email sending is now active with the correct method signatures from EmailService.
 *
 * FIX 5: getActivatedUserWithoutRole() no longer declares "throws BadRequestException".
 *   The method throws our custom unchecked BadRequestException — no checked declaration needed.
 */

@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl implements AuthService {


    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final SellerRepository sellerRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final JwtConfig jwtConfig;              // FIX 2: replaces @Value
    private final UserMapper userMapper;
    private final EmailService emailService;

    public AuthServiceImpl(UserRepository userRepository, CustomerRepository customerRepository, SellerRepository sellerRepository, RefreshTokenRepository refreshTokenRepository, EmailVerificationRepository emailVerificationRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService, JwtConfig jwtConfig, UserMapper userMapper, EmailService emailService) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.sellerRepository = sellerRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.emailVerificationRepository = emailVerificationRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.jwtConfig = jwtConfig;
        this.userMapper = userMapper;
        this.emailService = emailService;
    }


    // FIX 4: wired

    // ─────────────────────────────────────────────────────────────────────────
    // STEP 1 — Register
    // Creates user with role=null, accountStatus=PENDING
    // Sends 6-digit verification code by email
    // ─────────────────────────────────────────────────────────────────────────
    @Override
    public UserResponseDTO register(UserRequestDTO request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException("Email is already registered: " + request.email());
        }

        User user = new User();
        user.setFullName(request.fullName());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        // role          → stays null, set in Step 3 (completeProfile)
        // accountStatus → defaults to PENDING  (see User entity)
        // emailVerified → defaults to false

        User saved = userRepository.save(user);

        sendAndPersistVerificationCode(saved);

        log.info("User registered: id={}, email={}", saved.getId(), saved.getEmail());
        return userMapper.toResponseDTO(saved);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // STEP 2 — Verify Email
    // Validates 6-digit code, activates account, returns setup token
    // Setup token (role=PENDING in JWT claims) unlocks /complete-profile/**
    // ─────────────────────────────────────────────────────────────────────────
    @Override
    public EmailVerifiedResponseDTO verifyEmail(VerifyEmailRequestDTO request) {

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No account found for email: " + request.email()));

        if (user.getAccountStatus() == AccountStatus.ACTIVE) {
            throw new DuplicateResourceException("Email is already verified");     // FIX 3
        }

        if (user.getAccountStatus() == AccountStatus.SUSPENDED) {
            throw new BadRequestException("This account has been suspended. Contact support."); // FIX 3
        }

        EmailVerification verification = emailVerificationRepository
                .findTopByUserAndUsedFalseOrderByCreatedAtDesc(user)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No pending verification code found. Please register again."));

        if (verification.isExpired()) {
            throw new BadRequestException("Verification code has expired. Please request a new one."); // FIX 3
        }

        if (!verification.getCode().equals(request.code())) {
            throw new BadRequestException("Invalid verification code.");           // FIX 3
        }

        verification.setUsed(true);
        verification.setUsedAt(LocalDateTime.now());
        emailVerificationRepository.save(verification);

        user.setEmailVerified(true);
        user.setAccountStatus(AccountStatus.ACTIVE);
        User activated = userRepository.save(user);

        // Issue setup token — short-lived JWT with role=PENDING, valid 1 hour.
        // Client sends: Authorization: Bearer <setupToken> to /complete-profile/**
        String setupToken = jwtService.generateSetupToken(activated);

        emailService.sendWelcomeEmail(activated.getEmail(), activated.getFullName());

        log.info("Email verified for user id={}", activated.getId());
        return new EmailVerifiedResponseDTO(
                "Email verified. Please complete your profile.",
                activated.getId(),
                activated.getEmail(),
                setupToken
        );
    }

    // ─────────────────────────────────────────────────────────────────────────
    // STEP 3a — Complete Customer Profile
    // Requires setup token (role=PENDING) in Authorization header
    // Assigns CUSTOMER role, persists Customer entity, returns full JWT pair
    // ─────────────────────────────────────────────────────────────────────────
    @Override
    public AuthResponseDTO completeCustomerProfile(CustomerRequestDTO request, Long authenticatedUserId) {

        // The userId in the request body MUST match the user from the setup token.
        // Prevents User A from completing User B's profile.
        if (!authenticatedUserId.equals(request.userId())) {
            throw new ForbiddenException("You can only complete your own profile.");
        }

        User user = getActivatedUserWithoutRole(authenticatedUserId);

        if (customerRepository.existsByUserId(user.getId())) {
            throw new BadRequestException("A customer profile already exists for this account.");
        }

        // Assign role — requires role column to be updatable (User entity fix)
        user.setRole(Role.CUSTOMER);
        user = userRepository.save(user);

        Customer customer = new Customer();
        customer.setUser(user);
        customer.setImageURL(request.imageURL());
        customer.setSexe(request.sexe());
        customer.setPhone(request.phone());
        customerRepository.save(customer);

        log.info("Customer profile created for user id={}", user.getId());
        return buildFullAuthResponse(user);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // STEP 3b — Complete Seller Profile
    // Same flow as customer but assigns SELLER role and creates Seller entity
    // ─────────────────────────────────────────────────────────────────────────
    @Override
    public AuthResponseDTO completeSellerProfile(SellerRequestDTO request, Long authenticatedUserId) {

        if (!authenticatedUserId.equals(request.userId())) {
            throw new ForbiddenException("You can only complete your own profile.");
        }

        User user = getActivatedUserWithoutRole(authenticatedUserId);

        if (sellerRepository.existsByNbLicense(request.nbLicense())) {
            throw new DuplicateResourceException(
                    "License number already registered: " + request.nbLicense());
        }

        if (sellerRepository.existsByUserId(user.getId())) {
            throw new BadRequestException("A seller profile already exists for this account.");
        }

        user.setRole(Role.SELLER);
        user = userRepository.save(user);

        Seller seller = new Seller();
        seller.setUser(user);
        seller.setNb_license(request.nbLicense());
        seller.setImageURL(request.imageURL());
        seller.setSexe(request.sexe());
        seller.setPhone(request.phone());
        seller.setSite(request.site());
        // if (request.address() != null) seller.setAddress(addressMapper.toEntity(request.address()));
        sellerRepository.save(seller);

        log.info("Seller profile created for user id={}", user.getId());
        return buildFullAuthResponse(user);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Login
    // Requires: accountStatus=ACTIVE (email verified) AND role != null (profile done)
    // Returns: full access token (15 min) + refresh token (7 days)
    // ─────────────────────────────────────────────────────────────────────────
    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {

        try {
            // DaoAuthenticationProvider runs these checks:
            //   1. loadUserByUsername(email) — finds the user
            //   2. BCrypt.matches(rawPassword, encodedPassword)
            //   3. isEnabled()        — false for PENDING/SUSPENDED → DisabledException
            //   4. isAccountNonLocked() — false for SUSPENDED → LockedException
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );

        } catch (DisabledException ex) {
            userRepository.findByEmail(request.email()).ifPresent(u -> {
                if (u.getAccountStatus() == AccountStatus.PENDING) {
                    throw new AuthenticationException("Please verify your email before logging in.");
                }
            });
            throw new AuthenticationException("Your account has been suspended. Contact support.");

        } catch (BadCredentialsException ex) {
            throw new AuthenticationException("Invalid email or password."); // vague — don't reveal whether email exists
        }

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new AuthenticationException("Invalid email or password."));

        // Edge case: email verified but user never completed Step 3
        if (user.getRole() == null) {
            String setupToken = jwtService.generateSetupToken(user);
            throw new ProfileIncompleteException(
                    "Profile not completed. Use the setup token to complete your profile.",
                    user.getId(),
                    setupToken    // GlobalExceptionHandler serializes this in the 403 response body
            );
        }

        log.info("User logged in: id={}, role={}", user.getId(), user.getRole());
        return buildFullAuthResponse(user);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Refresh Token  (Rotation + Reuse Detection)
    //
    //   Valid token     → revoke old, issue new access token + new refresh token
    //   Revoked token   → theft assumed → revoke ALL sessions for this user
    //   Expired token   → revoke + tell user to login again
    // ─────────────────────────────────────────────────────────────────────────
    @Override
    @Transactional
    public TokenRefreshResponseDTO refreshToken(RefreshTokenRequestDTO request) {

        RefreshToken existing = refreshTokenRepository
                .findByToken(request.refreshToken())
                .orElseThrow(() -> new InvalidTokenException(
                        "Refresh token not found. Please login again."));

        // ── Reuse detection ──────────────────────────────────────────────────
        // A previously rotated token being presented again = either a confused client
        // or a stolen token. Either way: terminate every session for this user.
        if (existing.isRevoked()) {
            User compromisedUser = existing.getUser();
            int count = refreshTokenRepository.revokeAllByUser(compromisedUser);
            log.warn("SECURITY ALERT: Token reuse detected for user id={}. Revoked {} sessions.",
                    compromisedUser.getId(), count);
            throw new InvalidTokenException(
                    "Security alert: suspicious activity detected. All sessions terminated. Please login.");
        }

        // ── Expiry check ─────────────────────────────────────────────────────
        if (existing.isExpired()) {
            existing.setRevoked(true);
            existing.setRevokedAt(LocalDateTime.now());
            refreshTokenRepository.save(existing);
            throw new InvalidTokenException("Refresh token expired. Please login again.");
        }

        User user = existing.getUser();

        // ── Rotate: mark old as revoked, issue fresh pair ────────────────────
        existing.setRevoked(true);
        existing.setRevokedAt(LocalDateTime.now());
        refreshTokenRepository.save(existing);

        String newAccessToken = jwtService.generateAccessToken(user);
        RefreshToken newRefreshToken = createAndSaveRefreshToken(user);

        log.debug("Tokens rotated for user id={}", user.getId());
        return new TokenRefreshResponseDTO(
                newAccessToken,
                newRefreshToken.getToken(),
                "Bearer",
                jwtService.getAccessTokenExpiration()
        );
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Logout
    // Revokes the refresh token. Access token expires naturally (15 min).
    // ─────────────────────────────────────────────────────────────────────────
    @Override
    public void logout(RefreshTokenRequestDTO request) {
        refreshTokenRepository.findByToken(request.refreshToken()).ifPresent(token -> {
            if (!token.isRevoked()) {
                token.setRevoked(true);
                token.setRevokedAt(LocalDateTime.now());
                refreshTokenRepository.save(token);
                log.info("Logout: token revoked for user id={}", token.getUser().getId());
            }
            // Already revoked → silently succeed (idempotent)
        });
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Private Helpers
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Validates that a user:
     *  - Exists in the DB
     *  - Has accountStatus=ACTIVE (email verified)
     *  - Has role=null (profile not yet completed)
     * Used at the start of both completeCustomerProfile() and completeSellerProfile().
     *
     * FIX 5: No longer declares "throws BadRequestException" — it's unchecked.
     */
    private User getActivatedUserWithoutRole(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: id=" + userId));

        if (user.getAccountStatus() == AccountStatus.PENDING) {
            throw new BadRequestException("Email must be verified before completing your profile.");
        }

        if (user.getAccountStatus() == AccountStatus.SUSPENDED) {
            throw new BadRequestException("Account is suspended. Contact support.");
        }

        if (user.getRole() != null) {
            throw new BadRequestException("Profile is already completed for this account.");
        }

        return user;
    }

    /**
     * Builds the full AuthResponseDTO with a fresh access + refresh token pair.
     * Called after login() and after completeProfile().
     */
    private AuthResponseDTO buildFullAuthResponse(User user) {
        String accessToken = jwtService.generateAccessToken(user);
        RefreshToken refreshToken = createAndSaveRefreshToken(user);

        return new AuthResponseDTO(
                accessToken,
                refreshToken.getToken(),
                "Bearer",
                jwtService.getAccessTokenExpiration(),
                userMapper.toResponseDTO(user),
                List.of("ROLE_" + user.getRole().name())
        );
    }

    /**
     * Creates, persists and returns a new RefreshToken for the given user.
     * FIX 2: Uses JwtConfig directly for the expiration value
     * instead of the wrong @Value("${app.jwt.refresh-token-expiration}") key.
     */
    private RefreshToken createAndSaveRefreshToken(User user) {
        LocalDateTime expiresAt = LocalDateTime.now()
                .plusSeconds(jwtConfig.getRefreshTokenExpiration() / 1000); // ms → seconds
        RefreshToken token = new RefreshToken(user, expiresAt);
        return refreshTokenRepository.save(token);
    }

    /**
     * Generates a cryptographically secure 6-digit code, persists it,
     * invalidates any previous unused codes for the same user, and sends the email.
     */
    private void sendAndPersistVerificationCode(User user) {
        // Expire all prior unused codes so they can no longer be used
        emailVerificationRepository.markAllUnusedExpiredForUser(
                user.getId(), LocalDateTime.now());

        // SecureRandom for cryptographic quality — never use java.util.Random for security codes
        String code = String.valueOf(100_000 + new SecureRandom().nextInt(900_000));

        EmailVerification verification = new EmailVerification();
        verification.setUser(user);
        verification.setEmail(user.getEmail());
        verification.setCode(code);
        verification.setExpiresAt(LocalDateTime.now().plusMinutes(15));
        emailVerificationRepository.save(verification);

        // Send verification email via Thymeleaf template
        emailService.sendVerificationCode(user.getEmail(), code);

        // NEVER log the code in production — anyone with log access can bypass verification
        log.debug("Verification code generated and emailed for user id={}", user.getId());
    }

    @Override
    public ResendVerificationResponseDTO resendVerificationCode(ResendVerificationRequestDTO request) {

        // ── 1. User must exist and still be PENDING ───────────────────────────
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No account found for email: " + request.email()));

        if (user.getAccountStatus() == AccountStatus.ACTIVE) {
            throw new BadRequestException("This email is already verified. Please login.");
        }
        if (user.getAccountStatus() == AccountStatus.SUSPENDED) {
            throw new BadRequestException("This account has been suspended. Contact support.");
        }

        // ── 2. Check if a valid code is still alive ───────────────────────────
        //
        // findTopByUserAndUsedFalseOrderByCreatedAtDesc returns the most recent
        // unused code. If it exists and has NOT expired yet, we must not resend —
        // this prevents users from spamming the email endpoint.
        //
        // We calculate the exact seconds remaining so the frontend can show:
        //   "Please wait 4:32 before requesting a new code"
        //
        Optional<EmailVerification> activeCode = emailVerificationRepository
                .findTopByUserAndUsedFalseOrderByCreatedAtDesc(user);

        if (activeCode.isPresent() && !activeCode.get().isExpired()) {
            LocalDateTime expiresAt = activeCode.get().getExpiresAt();
            long secondsRemaining = ChronoUnit.SECONDS.between(LocalDateTime.now(), expiresAt);

            throw new BadRequestException(
                    "A verification code was already sent. " +
                            "Please wait " + secondsRemaining + " seconds before requesting a new one."
            );
        }

        // ── 3. Code is expired or used → send a fresh one ─────────────────────
        // sendAndPersistVerificationCode() already handles:
        //   - expiring all previous unused codes (so old codes can't be reused)
        //   - generating a new SecureRandom 6-digit code
        //   - persisting it with a 15-minute expiry
        //   - sending the HTML email
        sendAndPersistVerificationCode(user);

        // ── 4. Fetch the new code's expiry for the response ───────────────────
        LocalDateTime newExpiresAt = emailVerificationRepository
                .findTopByUserAndUsedFalseOrderByCreatedAtDesc(user)
                .map(EmailVerification::getExpiresAt)
                .orElse(LocalDateTime.now().plusMinutes(15)); // safe fallback

        long secondsRemaining = ChronoUnit.SECONDS.between(LocalDateTime.now(), newExpiresAt);

        log.info("Verification code resent for user id={}", user.getId());
        return new ResendVerificationResponseDTO(
                "A new verification code has been sent to " + request.email(),
                request.email(),
                newExpiresAt,
                secondsRemaining  // e.g. 900 → frontend starts counting down from 15:00
        );
    }

}