package com.hrafty.web_app.dto.response;

public record TokenRefreshResponseDTO(
        String accessToken,
        String tokenType,
        Long expiresIn
) {}