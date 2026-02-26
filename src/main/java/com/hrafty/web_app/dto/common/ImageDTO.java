package com.hrafty.web_app.dto.common;

public record ImageDTO(
        Long id,
        String filename,
        String url,
        Boolean isPrimary,
        Integer sortOrder,
        Long productId,
        Long serviceId
) {
}
