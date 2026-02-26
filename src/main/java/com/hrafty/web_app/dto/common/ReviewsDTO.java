package com.hrafty.web_app.dto.common;

import java.time.LocalDateTime;

public record ReviewsDTO(
        Long id,
        String comment,
        byte rating,
        Long customerId,
        Long serviceId,
        Long productId,
        String customerName,
        LocalDateTime createdAt
) {
}
