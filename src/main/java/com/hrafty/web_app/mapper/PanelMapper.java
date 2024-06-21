package com.hrafty.web_app.mapper;

import com.hrafty.web_app.dto.PanelDTO;
import com.hrafty.web_app.entities.Panel;
import com.hrafty.web_app.entities.Service;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring" ,uses={CustomerMapper.class,ServiceMapper.class})
public interface PanelMapper {
    PanelMapper INSTANCE = Mappers.getMapper(PanelMapper.class);

    default Panel map(Long id) {
        if (id == null) {
            return null;
        }
        Panel panel = new Panel();
        panel.setId(id);
        return panel;
    }

    default Long map(Panel panel) {
        if (panel == null) {
            return null;
        }
        return panel.getId();
    }

    @Mapping(source = "services", target = "services")
    PanelDTO toDTO(Panel entity);

    @Mapping(source = "services", target = "services")
    Panel toEntity(PanelDTO dto);

    default List<Long> mapServiceIds(List<Service> services) {
        if (services == null) {
            return null;
        }
        return services.stream()
                .map(Service::getId)
                .collect(Collectors.toList());
    }

    default List<Service> mapServices(List<Long> serviceIds) {
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
}
