package com.hrafty.web_app.Auth.Service;

import com.hrafty.web_app.Auth.AuthDTO;
import com.hrafty.web_app.Repository.CustomerRepository;
import com.hrafty.web_app.Repository.SellerRepository;
import com.hrafty.web_app.Repository.UserRepository;
import com.hrafty.web_app.dto.CustomerDTO;
import com.hrafty.web_app.dto.SellerDTO;
import com.hrafty.web_app.dto.UserDTO;
import com.hrafty.web_app.entities.Customer;
import com.hrafty.web_app.entities.Seller;
import com.hrafty.web_app.entities.User;
import com.hrafty.web_app.exception.EmailNotFoundException;
import com.hrafty.web_app.exception.RoleNoteFoundException;
import com.hrafty.web_app.mapper.CustomerMapper;
import com.hrafty.web_app.mapper.SellerMapper;
import com.hrafty.web_app.mapper.UserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class AuthService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;
    private final SellerMapper sellerMapper;
    private final SellerRepository sellerRepository;




    public AuthService(UserMapper userMapper, UserRepository userRepository, CustomerMapper customerMapper, CustomerRepository customerRepository, SellerMapper sellerMapper, SellerRepository sellerRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
        this.sellerMapper = sellerMapper;
        this.sellerRepository = sellerRepository;
    }


    @Transactional
    public SellerDTO registerSeller(UserDTO userDTO,SellerDTO sellerDTO) throws Exception {
        User userToSave=userRepository.save(userMapper.toEntity(userDTO));
        Seller seller = sellerMapper.toEntity(sellerDTO);
        seller.setUser(userToSave);
        Seller seller_save = sellerRepository.save(seller);
        return sellerMapper.toDTO(seller_save);
    }

    public CustomerDTO registerCustomer(UserDTO userDTO,CustomerDTO customerDTO) throws Exception {
        User userToSave=userRepository.save(userMapper.toEntity(userDTO));
        Customer customer = customerMapper.toEntity(customerDTO);
       customer.setUser(userToSave);
        Customer customer_saved = customerRepository.save(customer);
        return customerMapper.toDTO(customer_saved);
    }


    public AuthDTO login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new EmailNotFoundException("Invalid email or password");
        }
        User user = userOptional.get();
        UserDTO userDTO = userMapper.toDTO(user);
        AuthDTO authResponse = new AuthDTO();
        authResponse.setUser(userDTO);
        if (userDTO.getRole() != null) {
            String role = userDTO.getRole().name();
            if (role.equals("SELLER")) {
                Seller seller = user.getSeller();
                seller.getUser();
                authResponse.setSeller(sellerMapper.toDTO(seller));
            } else if (role.equals("CUSTOMER")) {
                Customer customer =user.getCustomer();
                customer.getUser();
                authResponse.setCustomer(customerMapper.toDTO(customer));
            } else {
                throw new RoleNoteFoundException("Invalid role");
            }
        }
        return authResponse;
    }


}
