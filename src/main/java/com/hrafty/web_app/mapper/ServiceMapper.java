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
            @Mapping(source = "reviews.id", target = "reviewId"),
            @Mapping(source = "panel.id", target = "panelId"),
    })
    ServiceDTO toDTO(Service entity);
    @Mappings({
            @Mapping(source = "sellerId", target = "seller"),
            @Mapping(source = "reviewId", target = "reviews"),
            @Mapping(source = "panelId", target = "panel"),
    })
    Service toEntity(ServiceDTO dto);

}
