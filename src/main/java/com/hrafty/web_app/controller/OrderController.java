package com.hrafty.web_app.controller;


import com.hrafty.web_app.services.Order;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/order")
public class OrderController {
    private final Order order;

    public OrderController(Order order) {
        this.order = order;
    }

//    @PostMapping("/api/v1/orders")
//    public ResponseEntity<OrderDTO> createOrder(@RequestBody List<OrderItemDTO> orderItemsDTO) {
//        return new ResponseEntity<>(orderDTO, HttpStatus.CREATED);
//
//    }


}

