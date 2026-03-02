package com.hrafty.web_app.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record PanelRequestDTO(
        @NotNull(message = "Customer ID is required")
        Long customerId,

        List<Long> serviceIds  // can be 0
) {}