package com.hrafty.web_app.dto.response;

import com.hrafty.web_app.entities.enums.Role;
import java.time.LocalDateTime;

public record UserResponseDTO(
        Long id,
        String fullName,
        String email,
        Role role,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}