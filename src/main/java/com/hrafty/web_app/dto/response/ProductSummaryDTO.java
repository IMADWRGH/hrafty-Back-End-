package com.hrafty.web_app.dto.response;

public record ProductSummaryDTO(
        Long id,
        String name,
        double price,
        String primaryImage,
        String category
) {}