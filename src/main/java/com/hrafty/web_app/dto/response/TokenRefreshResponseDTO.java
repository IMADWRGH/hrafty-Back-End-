package com.hrafty.web_app.dto.response;

public record TokenRefreshResponseDTO(
        String accessToken,
        String newRefreshToken,
        String tokenType,
        Long expiresIn
) {}