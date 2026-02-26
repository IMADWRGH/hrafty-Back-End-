package com.hrafty.web_app.dto.response;

import java.util.List;

public record AuthResponseDTO(
        String accessToken,
        String refreshToken,
        String tokenType,
        Long expiresIn,
        UserResponseDTO user,
        List<String> roles
) {}