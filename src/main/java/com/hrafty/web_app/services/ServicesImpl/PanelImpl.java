package com.hrafty.web_app.services.ServicesImpl;

import com.hrafty.web_app.Repository.CustomerRepository;
import com.hrafty.web_app.Repository.PanelRepository;
import com.hrafty.web_app.dto.PanelDTO;
import com.hrafty.web_app.entities.Customer;
import com.hrafty.web_app.entities.Panel;
import com.hrafty.web_app.exception.PanelNotFoundException;
import com.hrafty.web_app.mapper.PanelMapper;
import com.hrafty.web_app.services.PanelService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PanelImpl implements PanelService {
    private final PanelRepository panelRepository;
    private final PanelMapper panelMapper;
    private final CustomerRepository customerRepository;


    public PanelImpl(PanelRepository panelRepository, PanelMapper panelMapper, CustomerRepository customerRepository) {
        this.panelRepository = panelRepository;
        this.panelMapper = panelMapper;
        this.customerRepository = customerRepository;
    }


    @Override
    public PanelDTO getPanelById(Long id) {
        Panel panel = panelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Panel not found"));
        return panelMapper.toDTO(panel);
    }

    @Override
    public PanelDTO create(PanelDTO panelDTO) {
        Panel panel = panelMapper.toEntity(panelDTO);
        panel = panelRepository.save(panel);
        return panelMapper.toDTO(panel);
    }

    @Override
    public PanelDTO createById(PanelDTO panelDTO , Long customerID) {
        Customer customer =customerRepository.findById(customerID)
                .orElseThrow(()->new EntityNotFoundException("Customer not found"));

       Panel createPanel =panelMapper.toEntity(panelDTO);
       createPanel.setCustomer(customer);
       return panelMapper.toDTO(createPanel);

    }

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
    public List<PanelDTO> getPanelsByCustomer(Long customerId) {
        List<Panel> panels=panelRepository.findAllByCustomer_Id(customerId);
        for (Panel panel:panels){
            System.out.println(panel.toString());
        }
        return null;
    }
}
