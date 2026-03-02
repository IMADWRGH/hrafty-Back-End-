package com.hrafty.web_app.dto.response;

import java.time.LocalDateTime;

public record CustomerResponseDTO(
        Long id,
        String imageURL,
        String sexe,
        String phone,
        Long userId,
        String userFullName,
        String userEmail,
        Long panelId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}