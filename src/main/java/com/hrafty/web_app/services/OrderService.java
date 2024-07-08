package com.hrafty.web_app.services;

import com.hrafty.web_app.dto.OrderDTO;
import com.hrafty.web_app.dto.OrderItemDTO;

import java.util.List;

public interface OrderService {
    OrderDTO create(Long customerId, List<OrderItemDTO> orderItemsDTO) throws Exception;
    List<OrderDTO> getAllOrders();
    List<OrderDTO> getAllOrders(Long id);
    void updateOrders(Long id , OrderDTO orderDTO);
    OrderDTO getOrder(Long id);
    void  deleteOrder(Long id);
}
