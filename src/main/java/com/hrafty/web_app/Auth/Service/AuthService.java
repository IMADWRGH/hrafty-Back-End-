package com.hrafty.web_app.Auth.Service;

import com.hrafty.web_app.Auth.Auth;
import com.hrafty.web_app.Auth.AuthenticationResponse;
import com.hrafty.web_app.Repository.AddressRepository;
import com.hrafty.web_app.Repository.CustomerRepository;
import com.hrafty.web_app.Repository.SellerRepository;
import com.hrafty.web_app.Repository.UserRepository;
import com.hrafty.web_app.Security.JwtService;
import com.hrafty.web_app.dto.AddressDTO;
import com.hrafty.web_app.dto.CustomerDTO;
import com.hrafty.web_app.dto.SellerDTO;
import com.hrafty.web_app.dto.UserDTO;
import com.hrafty.web_app.entities.Address;
import com.hrafty.web_app.entities.Customer;
import com.hrafty.web_app.entities.Seller;
import com.hrafty.web_app.entities.User;
import com.hrafty.web_app.exception.EmailNotFoundException;
import com.hrafty.web_app.exception.InvalidPasswordException;
import com.hrafty.web_app.mapper.AddressMapper;
import com.hrafty.web_app.mapper.CustomerMapper;
import com.hrafty.web_app.mapper.SellerMapper;
import com.hrafty.web_app.mapper.UserMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;
    private final SellerMapper sellerMapper;
    private final SellerRepository sellerRepository;
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;




    public AuthService(UserMapper userMapper, UserRepository userRepository, CustomerMapper customerMapper, CustomerRepository customerRepository, SellerMapper sellerMapper, SellerRepository sellerRepository, AddressRepository addressRepository, AddressMapper addressMapper, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
        this.sellerMapper = sellerMapper;
        this.sellerRepository = sellerRepository;
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }



    public SellerDTO registerSeller(UserDTO userDTO, SellerDTO sellerDTO) throws Exception {
        User userToSave = userMapper.toEntity(userDTO);
        userToSave.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User savedUser = userRepository.save(userToSave);
        Address savedAddress = addressRepository.save(addressMapper.toEntity(sellerDTO.getAddressId()));
        Seller sellerToSave = sellerMapper.toEntity(sellerDTO);
        sellerToSave.setUser(savedUser);
        sellerToSave.setAddress(savedAddress);
        Seller savedSeller = sellerRepository.save(sellerToSave);
        return sellerMapper.toDTO(savedSeller);
    }

    public CustomerDTO registerCustomer(UserDTO userDTO,CustomerDTO customerDTO) throws Exception {
        User userToSave = userMapper.toEntity(userDTO);
        userToSave.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        User savedUser = userRepository.save(userToSave);
        Customer customer = customerMapper.toEntity(customerDTO);
        customer.setUser(savedUser);
        Customer customer_saved = customerRepository.save(customer);
        return customerMapper.toDTO(customer_saved);
    }


    public AuthenticationResponse login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new EmailNotFoundException("Invalid email or password");
        }
        User user = userOptional.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidPasswordException("Invalid email or password");
        }
        String token = jwtService.generateToken(user);
        UserDTO userDTO=userMapper.toDTO(user);
        AuthenticationResponse response = new AuthenticationResponse();
        response.setToken(token);
        response.setUser(userDTO);

        return response;
    }




}
