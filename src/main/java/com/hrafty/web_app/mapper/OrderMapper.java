package com.hrafty.web_app.mapper;

import com.hrafty.web_app.dto.request.OrderRequestDTO;
import com.hrafty.web_app.dto.response.OrderResponseDTO;
import com.hrafty.web_app.entities.Customer;
import com.hrafty.web_app.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {CustomerMapper.class, OrderItemMapper.class})
public interface OrderMapper {

    @Named("mapOrderById")
    default Order mapOrderById(Long id) {
        if (id == null) return null;
        Order order = new Order();
        order.setId(id);
        return order;
    }

    @Named("mapOrderToId")
    default Long mapOrderToId(Order order) {
        return order == null ? null : order.getId();
    }

    @Named("mapCustomerIdToCustomer")
    default Customer mapCustomerIdToCustomer(Long customerId) {
        if (customerId == null) return null;
        Customer customer = new Customer();
        customer.setId(customerId);
        return customer;
    }

    @Mapping(target = "id",         ignore = true)
    @Mapping(target = "customer",   source = "customerId", qualifiedByName = "mapCustomerIdToCustomer")
    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "status",     constant = "PENDING")
    Order toEntity(OrderRequestDTO dto);

    // FIX: Order entity does NOT extend Auditable and has no notes field.
    //
    //   OrderResponseDTO has:  notes, createdAt, updatedAt
    //   Order entity has:      id, customer, orderItems, totalPrice, status,
    //                          paymentMethod, paymentStatus, transactionId
    //
    //   Without these ignores, MapStruct throws compile errors:
    //     "No property named 'notes' exists in source parameter(s)"
    //     "No property named 'createdAt' exists in source parameter(s)"
    //     "No property named 'updatedAt' exists in source parameter(s)"
    //
    //   If you need audit timestamps on orders, add Order extends Auditable
    //   and run the DB migration. Until then, these fields will be null in responses.
    @Mapping(source = "customer.id",             target = "customerId")
    @Mapping(source = "customer.user.fullName",  target = "customerName")
    @Mapping(target = "notes",                   ignore = true)  // FIX: not in Order entity
    @Mapping(target = "createdAt",               ignore = true)  // FIX: Order doesn't extend Auditable
    @Mapping(target = "updatedAt",               ignore = true)  // FIX: Order doesn't extend Auditable
    OrderResponseDTO toResponseDTO(Order entity);

    default List<OrderResponseDTO> toResponseDTOList(List<Order> orders) {
        if (orders == null) return null;
        return orders.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }
}