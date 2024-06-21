package com.hrafty.web_app.services;

import com.hrafty.web_app.Repository.CustomerRepository;
import com.hrafty.web_app.Repository.UserRepository;
import com.hrafty.web_app.dto.CustomerDTO;
import com.hrafty.web_app.dto.UserDTO;
import com.hrafty.web_app.entities.Customer;
import com.hrafty.web_app.mapper.CustomerMapper;
import com.hrafty.web_app.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {


    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;
    private final UserMapper userMapper;

    private final UserService userService;


    public CustomerService(CustomerMapper customerMapper, CustomerRepository customerRepository, UserMapper userMapper, UserService userService) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
        this.userMapper = userMapper;
        this.userService = userService;
    }




}
