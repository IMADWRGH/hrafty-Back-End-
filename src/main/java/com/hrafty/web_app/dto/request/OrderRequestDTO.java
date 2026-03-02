package com.hrafty.web_app.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record OrderRequestDTO(
        @NotNull(message = "Customer ID is required")
        Long customerId,

        @NotEmpty(message = "Order must have at least one item")
        @Valid
        List<OrderItemRequestDTO> orderItems,

        String notes  // optional
) {}