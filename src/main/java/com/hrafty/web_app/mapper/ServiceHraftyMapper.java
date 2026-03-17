package com.hrafty.web_app.mapper;

import com.hrafty.web_app.dto.request.ServiceRequestDTO;
import com.hrafty.web_app.dto.response.ServiceResponseDTO;
import com.hrafty.web_app.entities.Panel;
import com.hrafty.web_app.entities.Seller;
import com.hrafty.web_app.entities.Service;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {SellerMapper.class, ImageMapper.class})
public interface ServiceHraftyMapper {

    @Named("mapServiceById")
    default Service mapServiceById(Long id) {
        if (id == null) return null;
        Service service = new Service();
        service.setId(id);
        return service;
    }

    @Named("mapServiceToId")
    default Long mapServiceToId(Service service) {
        return service == null ? null : service.getId();
    }

    @Named("mapSellerIdToSellerForService")
    default Seller mapSellerIdToSellerForService(Long sellerId) {
        if (sellerId == null) return null;
        Seller seller = new Seller();
        seller.setId(sellerId);
        return seller;
    }

    @Named("mapPanelIdToPanel")
    default Panel mapPanelIdToPanel(Long panelId) {
        if (panelId == null) return null;
        Panel panel = new Panel();
        panel.setId(panelId);
        return panel;
    }

    @Named("calculateAverageRating")
    default Double calculateAverageRating(Service service) {
        if (service.getReviews() == null || service.getReviews().isEmpty()) return 0.0;
        return service.getReviews().stream()
                .mapToInt(r -> r.getRating())
                .average()
                .orElse(0.0);
    }

    @Named("countReviews")
    default Integer countReviews(Service service) {
        return service.getReviews() != null ? service.getReviews().size() : 0;
    }

    @Mapping(target = "id",       ignore = true)
    @Mapping(target = "seller",   source = "sellerId", qualifiedByName = "mapSellerIdToSellerForService")
    @Mapping(target = "panel",    source = "panelId",  qualifiedByName = "mapPanelIdToPanel")
    @Mapping(target = "images",   ignore = true)
    @Mapping(target = "reviews",  ignore = true)
    Service toEntity(ServiceRequestDTO dto);


    @Mapping(source = "seller.id",             target = "sellerId")
    @Mapping(source = "seller.user.fullName",  target = "sellerName")
    @Mapping(source = "panel.id",              target = "panelId")
    @Mapping(source = ".",                     target = "averageRating", qualifiedByName = "calculateAverageRating")
    @Mapping(source = ".",                     target = "reviewCount",   qualifiedByName = "countReviews")
    @Mapping(target = "createdAt",             ignore = true)   // FIX: Service has no Auditable
    @Mapping(target = "updatedAt",             ignore = true)   // FIX: Service has no Auditable
    ServiceResponseDTO toResponseDTO(Service entity);
}