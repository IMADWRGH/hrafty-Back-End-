package com.hrafty.web_app.Security.jwt;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/*
 * FIX: This filter now reads the role FROM THE JWT CLAIMS — not from userDetails.getAuthorities().
 *
 * Why this matters:
 *   The 3-step registration flow requires that users with role=null in the DB
 *   can still authenticate with a "setup token" (role=PENDING in JWT claims).
 *
 *   Old code:
 *     UsernamePasswordAuthenticationToken(..., userDetails.getAuthorities())
 *     → For new users, User.getAuthorities() returns [] (role is null)
 *     → /complete-profile/** would return 403 even with a valid setup token
 *     → The entire PENDING flow was broken
 *
 *   New code:
 *     Reads role from jwtService.extractRole(jwt) → "PENDING", "CUSTOMER", etc.
 *     Builds a SimpleGrantedAuthority("ROLE_PENDING") from the token claim.
 *     SecurityConfig's .hasRole("PENDING") then correctly allows the request.
 *
 * The token was signed by us — its claims are trustworthy after signature verification.
 * We do NOT need to re-query the DB for the role on every request.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {



    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public JwtFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        // No Bearer token → pass through (public endpoints don't need one)
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);

        try {
            final String userEmail = jwtService.extractUsername(jwt);

            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

                if (jwtService.isTokenValid(jwt, userDetails)) {

                    /*
                     * Read role FROM TOKEN CLAIMS (not from userDetails).
                     *
                     * Token type   | role claim  | GrantedAuthority
                     * -------------|-------------|------------------
                     * Setup token  | "PENDING"   | ROLE_PENDING
                     * Access token | "CUSTOMER"  | ROLE_CUSTOMER
                     * Access token | "SELLER"    | ROLE_SELLER
                     * Access token | "ADMIN"     | ROLE_ADMIN
                     */
                    String roleFromToken = jwtService.extractRole(jwt);

                    List<SimpleGrantedAuthority> authorities = (roleFromToken != null && !roleFromToken.isBlank())
                            ? List.of(new SimpleGrantedAuthority("ROLE_" + roleFromToken))
                            : List.of();

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, authorities);

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

        } catch (JwtException e) {
            // Invalid / expired / malformed token.
            // Do NOT write a response here — SecurityConfig's AuthenticationEntryPoint
            // handles the JSON 401 response format.
            log.debug("JWT validation failed for [{}]: {}", request.getRequestURI(), e.getMessage());

        } catch (Exception e) {
            log.warn("Authentication error for [{}]: {}", request.getRequestURI(), e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}