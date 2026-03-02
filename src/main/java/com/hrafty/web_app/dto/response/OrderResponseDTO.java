package com.hrafty.web_app.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDTO(
        Long id,
        Long customerId,
        String customerName,
        List<OrderItemResponseDTO> orderItems,
        double totalPrice,
        String status,
        String notes,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}