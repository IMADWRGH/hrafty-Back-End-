package com.hrafty.web_app.controller;


import com.hrafty.web_app.dto.CustomerDTO;
import com.hrafty.web_app.services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/customer")
public class CustomerController {
    private final CustomerService customer;

    public CustomerController(CustomerService customerService) {
        this.customer = customerService;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<CustomerDTO> getCustomer(@PathVariable("id")Long id){
        CustomerDTO customerDTO=customer.getCustomer(id);
        return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
    }
}
