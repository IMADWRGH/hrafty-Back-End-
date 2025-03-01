package com.hrafty.web_app.mapper;



import com.hrafty.web_app.dto.ServiceDTO;
import com.hrafty.web_app.entities.Service;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = {SellerMapper.class, ReviewsMapper.class, PanelMapper.class})
public interface ServiceMapper {

    @Mappings({
            @Mapping(source = "seller.id", target = "sellerId"),
            @Mapping(source = "reviews", target = "reviewsDTOS"),
            @Mapping(source = "panel.id", target = "panelId"),
            @Mapping(source = "images", target = "images")

    })
    ServiceDTO toDTO(Service entity);
    @Mappings({
            @Mapping(source = "sellerId", target = "seller"),
            @Mapping(source = "reviewsDTOS", target = "reviews"),
            @Mapping(source = "panelId", target = "panel"),
            @Mapping(source = "images", target = "images")

    })
    Service toEntity(ServiceDTO dto);

}
