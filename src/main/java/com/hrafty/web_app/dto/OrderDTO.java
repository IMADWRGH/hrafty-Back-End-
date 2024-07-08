package com.hrafty.web_app.dto;

import java.util.List;

public record OrderDTO(Long id,Long customerId,List<OrderItemDTO> orderItems,double totalPrice) {
}
