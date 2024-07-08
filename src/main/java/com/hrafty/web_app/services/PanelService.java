package com.hrafty.web_app.services;

import com.hrafty.web_app.dto.CustomerDTO;
import com.hrafty.web_app.dto.PanelDTO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PanelService {
    public List<PanelDTO> getAllPanels();
    public PanelDTO getPanelById(Long id);
    public PanelDTO create(PanelDTO panelDTO);
    public PanelDTO updatePanel(Long id, PanelDTO updatedPanelDTO);
    public void deletePanel(Long id);
    public CustomerDTO getCustomerByPanelId(Long panelId);
}
