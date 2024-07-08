package com.hrafty.web_app.controller;


import com.hrafty.web_app.dto.OrderDTO;
import com.hrafty.web_app.dto.OrderItemDTO;
import com.hrafty.web_app.services.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/order")
public class OrderController {
    private final OrderService order;

    public OrderController(OrderService order) {
        this.order = order;
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<OrderDTO> createOrder(@PathVariable Long id, @RequestBody List<OrderItemDTO> orderItemsDTO) {
        try {
            OrderDTO createdOrder = order.create(id, orderItemsDTO);
            return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/getAllOrders")
    ResponseEntity<List<OrderDTO>> getAllOrders(){
        List<OrderDTO> orderDTOList=order.getAllOrders();
        return ResponseEntity.status(HttpStatus.OK).body(orderDTOList);
    }

    @GetMapping(path = "/getAllOrders/{id}")
    ResponseEntity<List<OrderDTO>> getAllOrders(@PathVariable Long id){
        List<OrderDTO> orderDTOList=order.getAllOrders(id);
        return ResponseEntity.status(HttpStatus.OK).body(orderDTOList);
    }



}

