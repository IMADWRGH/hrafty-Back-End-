package com.hrafty.web_app.services.ServicesImpl;

import com.hrafty.web_app.Repository.CustomerRepository;
import com.hrafty.web_app.Repository.UserRepository;
import com.hrafty.web_app.dto.CustomerDTO;
import com.hrafty.web_app.dto.UserDTO;
import com.hrafty.web_app.entities.Customer;
import com.hrafty.web_app.entities.User;
import com.hrafty.web_app.mapper.CustomerMapper;
import com.hrafty.web_app.mapper.UserMapper;
import com.hrafty.web_app.services.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class CustomerImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public CustomerImpl(CustomerRepository customerRepository, CustomerMapper customerMapper, UserMapper userMapper, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public CustomerDTO getCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("customer not found with id " + id));
        return customerMapper.toDTO(customer);
    }

    @Override
    public CustomerDTO create(UserDTO userDTO, CustomerDTO customerDTO) {
        User userToSave = userMapper.toEntity(userDTO);
        userToSave.setPassword(passwordEncoder.encode(userDTO.password()));
        User savedUser = userRepository.save(userToSave);
        Customer customer = customerMapper.toEntity(customerDTO);
        customer.setUser(savedUser);
        Customer customer_saved = customerRepository.save(customer);
        return customerMapper.toDTO(customer_saved);
    }

    @Override
    public void updateCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.toEntity(customerDTO);
        Customer customer_saved = customerRepository.save(customer);
        customerMapper.toDTO(customer_saved);
    }
}
