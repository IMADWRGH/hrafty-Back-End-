package com.hrafty.web_app.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ProductRequestDTO(
        @NotBlank(message = "Product name is required")
        @Size(max = 200, message = "Name must be less than 200 characters")
        String name,

        @Size(max = 1000, message = "Description must be less than 1000 characters")
        String description,

        @NotNull(message = "Price is required")
        @Positive(message = "Price must be positive")
        double price,

        @NotBlank(message = "Category is required")
        String category,

        @NotNull(message = "Stock quantity is required")
        @PositiveOrZero(message = "Stock cannot be negative")
        Integer stockQuantity,

        @NotNull(message = "Seller ID is required")
        Long sellerId
) {}