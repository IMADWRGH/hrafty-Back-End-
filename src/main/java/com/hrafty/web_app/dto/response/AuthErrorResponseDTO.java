package com.hrafty.web_app.dto.response;

import java.time.LocalDateTime;

public record AuthErrorResponseDTO(
        String error,
        String message,
        LocalDateTime timestamp
) {}
