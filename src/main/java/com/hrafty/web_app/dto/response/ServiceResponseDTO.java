package com.hrafty.web_app.dto.response;

import com.hrafty.web_app.dto.common.ImageDTO;
import java.time.LocalDateTime;
import java.util.List;

public record ServiceResponseDTO(
        Long id,
        String name,
        String description,
        List<ImageDTO> images,
        double price,
        String category,
        boolean status,
        Long sellerId,
        String sellerName,
        Double averageRating,
        Integer reviewCount,
        Long panelId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}