package com.hrafty.web_app.mapper;

import com.hrafty.web_app.dto.OrderItemDTO;
import com.hrafty.web_app.entities.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {OrderMapper.class, ProductMapper.class})
public interface OrderItemMapper {
    OrderItemMapper INSTANCE = Mappers.getMapper(OrderItemMapper.class);

    @Mappings({
            @Mapping(source = "product.id", target = "productId")
    })
    OrderItemDTO toDTO(OrderItem entity);

    @Mappings({
            @Mapping(source = "productId", target = "product")
    })
    OrderItem toEntity(OrderItemDTO dto);
}