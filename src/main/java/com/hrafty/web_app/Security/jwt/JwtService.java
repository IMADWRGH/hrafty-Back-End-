package com.hrafty.web_app.Security.jwt;

import com.hrafty.web_app.config.JwtConfig;
import com.hrafty.web_app.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/*
 * FIX: Upgraded from JJWT 0.11.x to 0.12.x API.
 *
 * JJWT 0.11.x (BROKEN — deprecated and removed):
 *   Jwts.parserBuilder()          → replaced by Jwts.parser()
 *   .setSigningKey(key)           → replaced by .verifyWith(key)
 *   .parseClaimsJws(token)        → replaced by .parseSignedClaims(token)
 *   .getBody()                    → replaced by .getPayload()
 *   Jwts.builder().setClaims()    → replaced by .claims()
 *   .setSubject()                 → replaced by .subject()
 *   .setIssuedAt() / .setExpiration() → replaced by .issuedAt() / .expiration()
 *   SignatureAlgorithm.HS256      → removed; signWith(key) auto-selects algorithm
 *
 * FIX: Key derivation.
 * Old code used: Keys.hmacShaKeyFor(secret.getBytes(UTF_8))
 * This is unsafe for short secrets — a 20-char string = 160 bits < required 256 bits.
 * New approach: secret is stored as Base64 in config, decoded to bytes here.
 * This guarantees the key is exactly as long as you configured it.
 * Generate a proper secret with: openssl rand -base64 64
 *
 * FIX: generateAccessToken now takes User directly.
 * Old signature: generateAccessToken(UserDetails, Long, String)
 * New signature: generateAccessToken(User) — simpler, avoids passing redundant args.
 *
 * NEW: generateSetupToken(User) — issues a limited JWT with role=PENDING.
 * Used after email verification. Only grants access to /api/auth/complete-profile/**.
 */
@Service
public class JwtService {

    private final JwtConfig jwtConfig;
    private final SecretKey secretKey;

    public JwtService(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
        // Base64-decode the configured secret → guaranteed correct key length
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtConfig.getSecretKey()));
    }

    // ─── Token Generation ─────────────────────────────────────────────────────

    /**
     * Full access token for authenticated users.
     * Contains: userId, real role (CUSTOMER/SELLER/ADMIN/MANAGER), type=ACCESS.
     * Expiry: configured via security.jwt.access-token-expiration (ms).
     */
    public String generateAccessToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("role", user.getRole().name());
        claims.put("type", "ACCESS");
        return buildToken(claims, user.getEmail(), jwtConfig.getAccessTokenExpiration());
    }

    /**
     * Setup token — issued ONLY after successful email verification (Step 2).
     * Contains: userId, role=PENDING, type=SETUP.
     * Expiry: 1 hour (hardcoded — not configurable, intentionally short-lived).
     *
     * SecurityConfig restricts /api/auth/complete-profile/** to hasRole("PENDING"),
     * so this token can ONLY be used for profile completion — nothing else.
     * After completeProfile succeeds, client discards this and stores the full token.
     */
    public String generateSetupToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("role", "PENDING");
        claims.put("type", "SETUP");
        return buildToken(claims, user.getEmail(), 3_600_000L); // 1 hour
    }

    private String buildToken(Map<String, Object> extraClaims, String subject, long expirationMs) {
        return Jwts.builder()
                .claims(extraClaims)                                        // 0.12.x API
                .subject(subject)                                           // 0.12.x API
                .issuedAt(new Date(System.currentTimeMillis()))             // 0.12.x API
                .expiration(new Date(System.currentTimeMillis() + expirationMs)) // 0.12.x API
                .signWith(secretKey)                                        // 0.12.x — algorithm auto-selected
                .compact();
    }

    // ─── Token Validation ─────────────────────────────────────────────────────

    /**
     * Validates: signature correct + not expired + subject matches UserDetails.
     * Called by JwtFilter after extracting the token from Authorization header.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            final String username = extractUsername(token);
            return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
        } catch (JwtException e) {
            return false;
        }
    }

    /**
     * Returns true if the token is a SETUP token (role=PENDING).
     * The JwtFilter uses this to allow setup tokens to reach /complete-profile/**
     * while blocking them from all other protected endpoints.
     */
    public boolean isSetupToken(String token) {
        return "SETUP".equals(extractClaim(token, claims -> claims.get("type", String.class)));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // ─── Claim Extraction ─────────────────────────────────────────────────────

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts role from JWT claims.
     * Returns "PENDING" for setup tokens, or the real role for access tokens.
     * JwtFilter uses this to set the correct GrantedAuthority on the SecurityContext.
     */
    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    public Long extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("userId", Long.class));
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                // 0.12.x API (was parserBuilder())
                .verifyWith(secretKey)                // 0.12.x API (was setSigningKey())
                .build()
                .parseSignedClaims(token)             // 0.12.x API (was parseClaimsJws())
                .getPayload();                        // 0.12.x API (was getBody())
    }

    // ─── Config Accessors ─────────────────────────────────────────────────────

    public long getAccessTokenExpiration() {
        return jwtConfig.getAccessTokenExpiration();
    }

    public long getRefreshTokenExpiration() {
        return jwtConfig.getRefreshTokenExpiration();
    }
}