package com.hrafty.web_app.services.ServicesImpl;

import com.hrafty.web_app.Repository.RefreshTokenRepository;
import com.hrafty.web_app.config.JwtConfig;
import com.hrafty.web_app.entities.RefreshToken;
import com.hrafty.web_app.entities.User;
import com.hrafty.web_app.services.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service

public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtConfig jwtConfig;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository, JwtConfig jwtConfig) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtConfig = jwtConfig;
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    /**
     * Creates and persists a new refresh token for the given user.
     *
     * FIX 1: Old code used LocalDateTime.from(Instant.now()) which throws
     * DateTimeException at runtime because Instant doesn't carry timezone info
     * and LocalDateTime.from() requires a ZonedDateTime or similar.
     *
     * Fix: Use LocalDateTime.now().plusSeconds(...) — clean, no conversion needed.
     *
     * FIX 2: Old code called refreshTokenRepository.deleteByUser(user) before creating
     * a new one — this breaks reuse detection. Old tokens must be REVOKED (not deleted)
     * so that if a stolen rotated token is ever replayed, we can detect it and kill
     * all sessions. See AuthServiceImpl.refreshToken() for the reuse detection logic.
     *
     * This method is used by AuthServiceImpl for the initial token creation (login,
     * profile completion). Rotation is handled entirely in AuthServiceImpl.refreshToken().
     */
    @Override
    @Transactional
    public RefreshToken createRefreshToken(User user) {
        // FIX: Use LocalDateTime arithmetic — no Instant/ZonedDateTime conversion needed
        LocalDateTime expiresAt = LocalDateTime.now()
                .plusSeconds(jwtConfig.getRefreshTokenExpiration() / 1000);

        // FIX: Removed deleteByUser() — old tokens stay revoked for reuse detection
        RefreshToken refreshToken = new RefreshToken(user, expiresAt);
        return refreshTokenRepository.save(refreshToken);
    }

    /**
     * FIX: Old code used ChronoLocalDateTime.from(Instant.now()) which also throws
     * DateTimeException for the same reason as above.
     *
     * Fix: Use LocalDateTime.now() directly.
     */
    @Override
    public boolean isTokenValid(RefreshToken token) {
        return token.getExpiresAt().isAfter(LocalDateTime.now()) && !token.isRevoked();
    }

    /**
     * Hard-deletes ALL refresh tokens for a user.
     * Use only for account deletion or admin-forced full logout.
     * For normal logout and rotation, use the revoke pattern in AuthServiceImpl.
     */
    @Override
    @Transactional
    public void deleteByUser(User user) {
        refreshTokenRepository.deleteByUser(user);
    }
}