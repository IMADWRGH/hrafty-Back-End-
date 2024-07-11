package com.hrafty.web_app.services;

import com.hrafty.web_app.dto.PanelDTO;

import java.util.List;


public interface PanelService {
    public PanelDTO getPanelById(Long id);
    public PanelDTO create(PanelDTO panelDTO);
    public PanelDTO updatePanel(Long id, PanelDTO updatedPanelDTO);
    public void deletePanel(Long id);
    public List<PanelDTO> getPanelsByCustomer(Long customerId);
}
