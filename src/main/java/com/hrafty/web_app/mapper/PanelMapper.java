package com.hrafty.web_app.mapper;

import com.hrafty.web_app.dto.request.PanelRequestDTO;
import com.hrafty.web_app.dto.response.PanelResponseDTO;
import com.hrafty.web_app.dto.common.PanelServiceDTO;
import com.hrafty.web_app.entities.Customer;
import com.hrafty.web_app.entities.Panel;
import com.hrafty.web_app.entities.Service;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {CustomerMapper.class})
public interface PanelMapper {

    @Named("mapPanelById")
    default Panel mapPanelById(Long id) {
        if (id == null) {
            return null;
        }
        Panel panel = new Panel();
        panel.setId(id);
        return panel;
    }

    @Named("mapPanelToId")
    default Long mapPanelToId(Panel panel) {
        if (panel == null) {
            return null;
        }
        return panel.getId();
    }

    @Named("mapCustomerIdToCustomerForPanel")
    default Customer mapCustomerIdToCustomerForPanel(Long customerId) {
        if (customerId == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setId(customerId);
        return customer;
    }

    @Named("mapServiceIdsToServices")
    default List<Service> mapServiceIdsToServices(List<Long> serviceIds) {
        if (serviceIds == null) {
            return null;
        }
        return serviceIds.stream()
                .map(id -> {
                    Service service = new Service();
                    service.setId(id);
                    return service;
                })
                .collect(Collectors.toList());
    }

    @Named("servicesToPanelServiceDTOs")
    default List<PanelServiceDTO> servicesToPanelServiceDTOs(List<Service> services) {
        if (services == null) {
            return null;
        }
        return services.stream()
                .map(this::toPanelServiceDTO)
                .collect(Collectors.toList());
    }

    default PanelServiceDTO toPanelServiceDTO(Service service) {
        if (service == null) {
            return null;
        }
        String primaryImage = null;
        if (service.getImages() != null && !service.getImages().isEmpty()) {
            primaryImage = service.getImages().get(0).getUrl();
        }
        return new PanelServiceDTO(
                service.getId(),
                service.getName(),
                service.getPrice(),
                primaryImage,
                service.isStatus()
        );
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", source = "customerId", qualifiedByName = "mapCustomerIdToCustomerForPanel")
    @Mapping(target = "services", ignore = true)
    Panel toEntity(PanelRequestDTO dto);

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "customer.user.fullName", target = "customerName")
    @Mapping(source = "services", target = "services", qualifiedByName = "servicesToPanelServiceDTOs")
    PanelResponseDTO toResponseDTO(Panel entity);
}