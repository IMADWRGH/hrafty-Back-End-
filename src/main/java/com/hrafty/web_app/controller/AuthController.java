package com.hrafty.web_app.controller;

import com.hrafty.web_app.dto.request.*;
import com.hrafty.web_app.dto.response.*;
import com.hrafty.web_app.entities.User;
import com.hrafty.web_app.Security.jwt.JwtService;
import com.hrafty.web_app.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")

public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    // POST /api/auth/register — Step 1
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody @Valid UserRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(dto));
    }

    // POST /api/auth/verify-email — Step 2
    @PostMapping("/verify-email")
    public ResponseEntity<EmailVerifiedResponseDTO> verifyEmail(
            @RequestBody @Valid VerifyEmailRequestDTO dto) {
        return ResponseEntity.ok(authService.verifyEmail(dto));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // POST /api/auth/resend-verification — NEW
    //
    // Called when the user did not receive the code or it expired.
    //
    // Success response (200):
    // {
    //   "message": "A new verification code has been sent to user@example.com",
    //   "email": "user@example.com",
    //   "expiresAt": "2025-03-03T11:15:00",
    //   "secondsRemaining": 900
    // }
    //
    // Error response (400) if code is still valid:
    // {
    //   "error": "BAD_REQUEST",
    //   "message": "A verification code was already sent. Please wait 347 seconds...",
    //   "timestamp": "..."
    // }
    // ─────────────────────────────────────────────────────────────────────────
    @PostMapping("/resend-verification")
    public ResponseEntity<ResendVerificationResponseDTO> resendVerificationCode(
            @RequestBody @Valid ResendVerificationRequestDTO dto) {
        return ResponseEntity.ok(authService.resendVerificationCode(dto));
    }

    // POST /api/auth/complete-profile/customer — Step 3a
    @PostMapping("/complete-profile/customer")
    @PreAuthorize("hasRole('PENDING')")
    public ResponseEntity<AuthResponseDTO> completeCustomerProfile(
            @RequestBody @Valid CustomerRequestDTO dto,
            Authentication authentication) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.completeCustomerProfile(dto, userId));
    }

    // POST /api/auth/complete-profile/seller — Step 3b
    @PostMapping("/complete-profile/seller")
    @PreAuthorize("hasRole('PENDING')")
    public ResponseEntity<AuthResponseDTO> completeSellerProfile(
            @RequestBody @Valid SellerRequestDTO dto,
            Authentication authentication) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.completeSellerProfile(dto, userId));
    }

    // POST /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    // POST /api/auth/refresh-token
    @PostMapping("/refresh-token")
    public ResponseEntity<TokenRefreshResponseDTO> refreshToken(
            @RequestBody @Valid RefreshTokenRequestDTO dto) {
        return ResponseEntity.ok(authService.refreshToken(dto));
    }

    // POST /api/auth/logout
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody @Valid RefreshTokenRequestDTO dto) {
        authService.logout(dto);
        return ResponseEntity.noContent().build();
    }
}


// ─────────────────────────────────────────────────────────────────────────────
// SecurityConfig change — add "/api/auth/resend-verification" to permitAll()
//
// Replace your current permitAll block with this:
// ─────────────────────────────────────────────────────────────────────────────

/*
    .requestMatchers(
        "/api/auth/register",
        "/api/auth/verify-email",
        "/api/auth/resend-verification",   // ← add this
        "/api/auth/login",
        "/api/auth/refresh-token"
    ).permitAll()
*/