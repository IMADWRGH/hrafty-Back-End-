package com.hrafty.web_app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrafty.web_app.Security.jwt.JwtFilter;
import com.hrafty.web_app.Security.userdetails.UserDetailsServiceImpl;
import com.hrafty.web_app.dto.response.AuthErrorResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final ObjectMapper objectMapper;

    public SecurityConfig(JwtFilter jwtFilter, ObjectMapper objectMapper, UserDetailsServiceImpl userDetailsService) {
        this.jwtFilter = jwtFilter;
        this.objectMapper = objectMapper;
        this.userDetailsService = userDetailsService;
    }

    /*
     * FIX: Inject UserDetailsServiceImpl directly.
     *
     * Old code had two bugs:
     *   Bug 1 — userDetailsService(null):
     *     The old code called .authenticationProvider(authenticationProvider(userDetailsService(null)))
     *     This passes null as the UserRepository argument to the userDetailsService @Bean method.
     *     Spring doesn't call @Bean methods through normal injection in this context —
     *     it calls them directly, so UserRepository is literally null → NPE at startup.
     *
     *   Bug 2 — CORS declared but never activated:
     *     corsConfigurationSource() was defined as a @Bean but was never registered
     *     in the filter chain with .cors(cors -> cors.configurationSource(...)).
     *     CORS headers were never being added to responses.
     */
    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)

                // FIX: Actually activates the CORS config (was missing before)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // Custom 401 — returns JSON instead of a redirect to login page
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            objectMapper.writeValue(response.getWriter(), new AuthErrorResponseDTO(
                                    "UNAUTHORIZED",
                                    "Authentication required: " + authException.getMessage(),
                                    LocalDateTime.now()
                            ));
                        })
                        // Custom 403 — returns JSON
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpStatus.FORBIDDEN.value());
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            objectMapper.writeValue(response.getWriter(), new AuthErrorResponseDTO(
                                    "FORBIDDEN",
                                    "Access denied: you do not have the required role",
                                    LocalDateTime.now()
                            ));
                        })
                )

                .authorizeHttpRequests(auth -> auth

                        // ── Public: no token required ──────────────────────────────
                        .requestMatchers("/api/v1/auth/**",
                                "/api/auth/register",
                                "/api/auth/verify-email",
                                "/api/auth/login",
                                "/api/auth/refresh-token",
                                "/api/au th/resend-verification"   // add to permitAll() list
                        ).permitAll()

                        // Static resources (UI pages)
                        .requestMatchers(
                                "/*.html",
                                "/css/**",
                                "/js/**",
                                "/images/**"
                        ).permitAll()

                        // ── Setup token required (role=PENDING in JWT) ─────────────
                        // JwtFilter reads role from token claims → ROLE_PENDING authority set.
                        // Regular CUSTOMER/SELLER tokens will get 403 here (correct behaviour).
                        .requestMatchers("/api/auth/complete-profile/**").hasRole("PENDING")

                        // ── Any valid access token ─────────────────────────────────
                        .requestMatchers("/api/auth/logout").authenticated()

                        // ── Role-based access ──────────────────────────────────────
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/manager/**").hasRole("MANAGER")
                        .requestMatchers("/api/seller/**").hasRole("SELLER")
                        .requestMatchers("/api/customer/**").hasRole("CUSTOMER")

                        .anyRequest().authenticated()
                )

                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(
                "http://localhost:4200"
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    // FIX: No-arg method — uses the injected UserDetailsServiceImpl field.
    // No more userDetailsService(null) NPE.
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        // Hides whether account disabled vs bad credentials — set false in dev if needed
        provider.setHideUserNotFoundExceptions(true);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12); // strength=12 is production standard
    }
}