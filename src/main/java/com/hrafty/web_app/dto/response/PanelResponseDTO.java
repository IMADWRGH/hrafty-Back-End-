package com.hrafty.web_app.dto.response;

import com.hrafty.web_app.dto.common.PanelServiceDTO;

import java.time.LocalDateTime;
import java.util.List;

public record PanelResponseDTO(
        Long id,
        Long customerId,
        String customerName,
        List<PanelServiceDTO> services,
        LocalDateTime createdAt
) {}
