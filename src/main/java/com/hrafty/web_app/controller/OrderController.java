package com.hrafty.web_app.controller;


import com.hrafty.web_app.dto.OrderDTO;
import com.hrafty.web_app.dto.OrderItemDTO;
import com.hrafty.web_app.services.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/order")
public class OrderController {
    private final Order order;

    public OrderController(Order order) {
        this.order = order;
    }

    @PostMapping("/add/{customerId}")
    public ResponseEntity<OrderDTO> createOrder(@PathVariable Long customerId, @RequestBody List<OrderItemDTO> orderItemsDTO) {
        try {
            OrderDTO createdOrder = order.create(customerId, orderItemsDTO);
            return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

