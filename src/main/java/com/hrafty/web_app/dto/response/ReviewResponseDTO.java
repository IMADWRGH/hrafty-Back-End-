package com.hrafty.web_app.dto.response;

import java.time.LocalDateTime;

public record ReviewResponseDTO(
        Long id,
        Byte rating,
        String comment,
        Long customerId,
        String customerName,
        String customerImageURL,
        Long serviceId,
        Long productId,
        String serviceName,
        String productName,
        LocalDateTime createdAt
) {}