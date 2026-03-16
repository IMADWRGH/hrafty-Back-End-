package com.hrafty.web_app.services;

import com.hrafty.web_app.dto.request.PanelRequestDTO;
import com.hrafty.web_app.dto.response.PanelResponseDTO;
import com.hrafty.web_app.dto.common.PageResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PanelService {

    PanelResponseDTO getPanelById(Long id);

    PanelResponseDTO getPanelByCustomerId(Long customerId);

    PanelResponseDTO create(PanelRequestDTO panelRequestDTO);

    PanelResponseDTO createByCustomerId(PanelRequestDTO panelRequestDTO, Long customerId);

    PanelResponseDTO updatePanel(Long id, PanelRequestDTO updatedPanelRequestDTO);

    void deletePanel(Long id);

    List<PanelResponseDTO> getAllPanels();

    PageResponseDTO<PanelResponseDTO> getAllPanels(Pageable pageable);

    List<PanelResponseDTO> getPanelsByCustomerId(Long customerId);

    // NEW METHODS
    PanelResponseDTO addServiceToPanel(Long panelId, Long serviceId);

    PanelResponseDTO removeServiceFromPanel(Long panelId, Long serviceId);

    boolean existsById(Long id);

    boolean existsByCustomerId(Long customerId);
}