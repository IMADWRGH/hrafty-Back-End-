package com.hrafty.web_app.Security.userdetails;

import com.hrafty.web_app.entities.User;
import com.hrafty.web_app.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*
     * Loads the user from DB by email.
     * Called by:
     *   1. DaoAuthenticationProvider during login (authenticationManager.authenticate())
     *   2. JwtFilter on every authenticated request
     *
     * Returns UserDetailsImpl (wraps User entity, copies enabled/locked state).
     * After the UserDetailsImpl.build() fix, this no longer NPEs for role=null users.
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        return UserDetailsImpl.build(user);
    }
}