package com.hrafty.web_app.dto.response;

public record OrderItemResponseDTO(
        Long id,
        Long productId,
        String productName,
        String productImage,
        int quantity,
        double unitPrice,
        double totalPrice
) {}