package com.hrafty.web_app.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ServiceRequestDTO(
        @NotBlank(message = "Service name is required")
        @Size(max = 200)
        String name,

        @Size(max = 1000)
        String description,

        @NotNull(message = "Price is required")
        @Positive(message = "Price must be positive")
        double price,

        @NotBlank(message = "Category is required")
        String category,

        boolean status,

        @NotNull(message = "Seller ID is required")
        Long sellerId,

        Long panelId  // can be null
) {}