package com.hrafty.web_app.dto.response;

import com.hrafty.web_app.dto.common.ImageDTO;
import java.time.LocalDateTime;
import java.util.List;

public record ProductResponseDTO(
        Long id,
        List<ImageDTO> images,
        String name,
        String description,
        double price,
        String category,
        Integer stockQuantity,
        Boolean active,
        Long sellerId,
        String sellerName,      // Denormalized
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}