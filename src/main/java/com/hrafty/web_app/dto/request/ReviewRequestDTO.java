package com.hrafty.web_app.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReviewRequestDTO(
        @NotNull(message = "Rating is required")
        @Min(value = 1, message = "Rating must be at least 1")
        @Max(value = 5, message = "Rating must be at most 5")
        Byte rating,

        @Size(max = 1000, message = "Comment must be less than 1000 characters")
        String comment,

        @NotNull(message = "Customer ID is required")
        Long customerId,

        Long serviceId,
        Long productId
) {}