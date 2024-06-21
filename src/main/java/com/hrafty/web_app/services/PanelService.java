package com.hrafty.web_app.services;

import com.hrafty.web_app.Repository.PanelRepository;
import com.hrafty.web_app.dto.CustomerDTO;
import com.hrafty.web_app.dto.PanelDTO;
import com.hrafty.web_app.entities.Customer;
import com.hrafty.web_app.entities.Panel;
import com.hrafty.web_app.exception.PanelNotFoundException;
import com.hrafty.web_app.mapper.PanelMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PanelService {
    private final PanelRepository panelRepository;
    private final PanelMapper panelMapper;

    public PanelService(PanelRepository panelRepository, PanelMapper panelMapper) {
        this.panelRepository = panelRepository;
        this.panelMapper = panelMapper;
    }

    public List<PanelDTO> getAllPanels() {
        List<Panel> panels=panelRepository.findAll();
        List<PanelDTO> panelDTOS=new ArrayList<>();
        for (Panel panel:panels){
            panelDTOS.add(panelMapper.toDTO(panel));
        }
        return panelDTOS;
//        return panelRepository.findAll().stream()
//                .map(panelMapper::toDTO)
//                .collect(Collectors.toList());
    }

    public PanelDTO getPanelById(Long id) {
        Panel panel = panelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Panel not found"));
        return panelMapper.toDTO(panel);
    }

    public PanelDTO createPanel(PanelDTO panelDTO) {
        Panel panel = panelMapper.toEntity(panelDTO);
        panel = panelRepository.save(panel);
        return panelMapper.toDTO(panel);
    }

    public PanelDTO updatePanel(Long id, PanelDTO updatedPanelDTO) {
        Panel panel = panelRepository.findById(id)
                .orElseThrow(() -> new PanelNotFoundException("Panel not found"));
        if (panel != null) {
            Panel updatedPanel = panelMapper.toEntity(updatedPanelDTO);
            updatedPanel.setId(panel.getId());
            updatedPanel = panelRepository.save(updatedPanel);
            return panelMapper.toDTO(updatedPanel);
        }
        return null;
    }

    public void deletePanel(Long id) {
        panelRepository.deleteById(id);
    }

    public CustomerDTO getCustomerByPanelId(Long panelId) {
        Panel panel = panelRepository.findById(panelId)
                .orElseThrow(() -> new PanelNotFoundException("Panel not found"));
        PanelDTO panelDTO=panelMapper.toDTO(panel);
        if(panelDTO != null) {
            return panelDTO.getCustomer();
        }
        return null;
    }
}
