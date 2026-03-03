package com.hrafty.web_app.dto.response;

import java.time.LocalDateTime;

public record ResendVerificationResponseDTO(
        String message,
        String email,
        LocalDateTime expiresAt,   // in angular cloud show live countdown
        long secondsRemaining      // convenience field: (expiresAt - now) in seconds
) {}