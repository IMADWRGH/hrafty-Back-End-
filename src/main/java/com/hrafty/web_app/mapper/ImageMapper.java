package com.hrafty.web_app.mapper;

import com.hrafty.web_app.dto.common.ImageDTO;
import com.hrafty.web_app.entities.Image;
import com.hrafty.web_app.entities.Product;
import com.hrafty.web_app.entities.Service;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ImageMapper {

    @Named("mapImageById")
    default Image mapImageById(Long id) {
        if (id == null) return null;
        Image image = new Image();
        image.setId(id);
        return image;
    }

    @Named("mapImageToId")
    default Long mapImageToId(Image image) {
        return image == null ? null : image.getId();
    }

    @Named("mapProductIdToProduct")
    default Product mapProductIdToProduct(Long productId) {
        if (productId == null) return null;
        Product product = new Product();
        product.setId(productId);
        return product;
    }

    @Named("mapServiceIdToService")
    default Service mapServiceIdToService(Long serviceId) {
        if (serviceId == null) return null;
        Service service = new Service();
        service.setId(serviceId);
        return service;
    }

    // FIX 1: sortOrder is in ImageDTO but NOT in Image entity.
    //   Without ignore=true MapStruct throws: "Unmapped target property: sortOrder"
    // FIX 2: isPrimary mapping works NOW because Image entity has
    //   getIsPrimary() / setIsPrimary() (see fixed Image.java).
    //   In the original Image.java, isPrimary had NO getter/setter at all
    //   — MapStruct could not access it and would throw a compile error.
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "service.id", target = "serviceId")
    @Mapping(target = "sortOrder",  ignore = true)
    ImageDTO toDTO(Image entity);

    @Mapping(target = "id",      ignore = true)
    @Mapping(target = "product", source = "productId", qualifiedByName = "mapProductIdToProduct")
    @Mapping(target = "service", source = "serviceId", qualifiedByName = "mapServiceIdToService")
    Image toEntity(ImageDTO dto);
    // sortOrder is source-only — no target in entity.
    // MapStruct silently ignores unmapped SOURCE properties by default.
}