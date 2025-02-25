package com.hrafty.web_app.services;


import com.hrafty.web_app.dto.CustomerDTO;
import com.hrafty.web_app.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

public interface CustomerService {
    CustomerDTO getCustomer(Long id);
    CustomerDTO create(UserDTO userDTO, CustomerDTO customerDTO, MultipartFile file);

    void updateCustomer(CustomerDTO customerDTO);
}
