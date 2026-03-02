package com.hrafty.web_app.dto.response;

import java.util.List;

public record AuthResponseDTO(  // Login response & Profile completed, tokens issued
        String accessToken,
        String refreshToken,
        String tokenType, // "Bearer"
        Long expiresIn,
        UserResponseDTO user,
        List<String> roles
) {}