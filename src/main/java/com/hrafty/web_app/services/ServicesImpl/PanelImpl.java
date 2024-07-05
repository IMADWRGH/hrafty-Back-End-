package com.hrafty.web_app.services.ServicesImpl;

import com.hrafty.web_app.Repository.PanelRepository;
import com.hrafty.web_app.dto.CustomerDTO;
import com.hrafty.web_app.dto.PanelDTO;
import com.hrafty.web_app.entities.Panel;
import com.hrafty.web_app.exception.PanelNotFoundException;
import com.hrafty.web_app.mapper.PanelMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PanelImpl implements com.hrafty.web_app.services.Panel {
    private final PanelRepository panelRepository;
    private final PanelMapper panelMapper;


    public PanelImpl(PanelRepository panelRepository, PanelMapper panelMapper) {
        this.panelRepository = panelRepository;
        this.panelMapper = panelMapper;
    }

    @Override
    public List<PanelDTO> getAllPanels() {
        List<Panel> panels=panelRepository.findAll();
        List<PanelDTO> panelDTOS=new ArrayList<>();
        for (Panel panel:panels){
            panelDTOS.add(panelMapper.toDTO(panel));
        }
        return panelDTOS;
    }

    @Override
    public PanelDTO getPanelById(Long id) {
        Panel panel = panelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Panel not found"));
        return panelMapper.toDTO(panel);
    }

    @Override
    public PanelDTO create(PanelDTO panelDTO) {
        return null;
    }

//    @Override
//    public PanelDTO createPanel(PanelDTO panelDTO) {
//        Panel panel = panelMapper.toEntity(panelDTO);
//        panel = panelRepository.save(panel);
//        return panelMapper.toDTO(panel);
//    }

    @Override
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

    @Override
    public void deletePanel(Long id) {
        panelRepository.deleteById(id);

    }

    @Override
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
