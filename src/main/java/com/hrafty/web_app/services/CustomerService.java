package com.hrafty.web_app.services;


import com.hrafty.web_app.dto.common.PageResponseDTO;
import com.hrafty.web_app.dto.request.CustomerRequestDTO;
import com.hrafty.web_app.dto.request.UserRequestDTO;
import com.hrafty.web_app.dto.response.CustomerResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CustomerService {
    CustomerResponseDTO getCustomer(Long id);

    CustomerResponseDTO getCustomerByUserId(Long userId);

    CustomerResponseDTO create(UserRequestDTO userRequestDTO, CustomerRequestDTO customerRequestDTO, MultipartFile file);

    CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO customerRequestDTO);

    // NEW METHODS
    List<CustomerResponseDTO> getAllCustomers();

    PageResponseDTO<CustomerResponseDTO> getAllCustomers(Pageable pageable);

    void deleteCustomer(Long id);

    CustomerResponseDTO updateCustomerImage(Long id, MultipartFile file);

    boolean existsById(Long id);

    long countCustomers();

}
