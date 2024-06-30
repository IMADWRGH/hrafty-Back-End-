package com.hrafty.web_app.mapper;

import com.hrafty.web_app.dto.OrderDTO;
import com.hrafty.web_app.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {UserMapper.class, OrderItemMapper.class})
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    default Order map(Long id) {
        if (id == null) {
            return null;
        }
        Order order = new Order();
        order.setId(id);
        return order;
    }

    default Long map(Order order) {
        if (order == null) {
            return null;
        }
        return order.getId();
    }

    @Mappings({
            @Mapping(source = "customer.id", target = "customerId"),
            @Mapping(source = "orderItems", target = "orderItems")
    })
    OrderDTO toDTO(Order entity);

    @Mappings({
            @Mapping(source = "customerId", target = "customer.id"),
            @Mapping(source = "orderItems", target = "orderItems")
    })
    Order toEntity(OrderDTO dto);
}
