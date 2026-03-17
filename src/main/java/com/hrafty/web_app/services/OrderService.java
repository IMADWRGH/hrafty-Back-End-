package com.hrafty.web_app.services;



import com.hrafty.web_app.dto.common.PageResponseDTO;
import com.hrafty.web_app.dto.request.OrderRequestDTO;
import com.hrafty.web_app.dto.response.OrderResponseDTO;
import com.hrafty.web_app.entities.enums.OrderStatus;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    OrderResponseDTO create(Long customerId, OrderRequestDTO orderRequestDTO) throws Exception;

    List<OrderResponseDTO> getAllOrders();

    PageResponseDTO<OrderResponseDTO> getAllOrders(Pageable pageable);

    List<OrderResponseDTO> getOrdersByCustomerId(Long customerId);

    PageResponseDTO<OrderResponseDTO> getOrdersByCustomerId(Long customerId, Pageable pageable);

    OrderResponseDTO getOrder(Long id);

    OrderResponseDTO updateOrder(Long id, OrderRequestDTO orderRequestDTO);

    OrderResponseDTO updateOrderStatus(Long id, OrderStatus status);

    void deleteOrder(Long id);


    List<OrderResponseDTO> getOrdersByStatus(OrderStatus status);

    PageResponseDTO<OrderResponseDTO> getOrdersByStatus(OrderStatus status, Pageable pageable);

    double calculateTotalRevenue();

    long countOrders();

    long countOrdersByStatus(OrderStatus status);

    boolean existsById(Long id);
}
