package com.hrafty.web_app.dto.response;

public record EmailVerifiedResponseDTO(
        String message,
        Long userId,
        String email
) {}