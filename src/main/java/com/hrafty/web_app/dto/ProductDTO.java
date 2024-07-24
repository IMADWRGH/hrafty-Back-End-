package com.hrafty.web_app.dto;

import java.util.List;

public record ProductDTO(
        Long id,
        List<ImageDTO> images,
        String name,
        String description,
        double price,
        String category,
        Long sellerId
) {
}
