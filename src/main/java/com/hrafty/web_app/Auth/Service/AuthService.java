package com.hrafty.web_app.Auth.Service;

import com.hrafty.web_app.Auth.AuthenticationResponse;
import com.hrafty.web_app.Repository.UserRepository;
import com.hrafty.web_app.Security.JwtService;
import com.hrafty.web_app.dto.UserDTO;
import com.hrafty.web_app.entities.User;
import com.hrafty.web_app.exception.EmailNotFoundException;
import com.hrafty.web_app.exception.InvalidPasswordException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;


@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthenticationResponse login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException("Email not found"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }
        String token = jwtService.generateToken(user);
        UserDTO userDTO = new UserDTO(user.getId(), user.getFullName(), user.getEmail(),null, user.getRole());
        return new AuthenticationResponse(token, userDTO);
    }




}
