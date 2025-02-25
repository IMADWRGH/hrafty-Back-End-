package com.hrafty.web_app.services.ServicesImpl;

import com.hrafty.web_app.Repository.CustomerRepository;
import com.hrafty.web_app.Repository.UserRepository;
import com.hrafty.web_app.dto.CustomerDTO;
import com.hrafty.web_app.dto.UserDTO;
import com.hrafty.web_app.entities.Customer;
import com.hrafty.web_app.entities.User;
import com.hrafty.web_app.exception.EmailAlreadyExistsException;
import com.hrafty.web_app.mapper.CustomerMapper;
import com.hrafty.web_app.mapper.UserMapper;
import com.hrafty.web_app.services.CustomerService;
import com.hrafty.web_app.services.ImageService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class CustomerImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;


    public CustomerImpl(CustomerRepository customerRepository, CustomerMapper customerMapper, UserMapper userMapper, UserRepository userRepository, PasswordEncoder passwordEncoder, ImageService imageService) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.imageService = imageService;
    }

    @Override
    public CustomerDTO getCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id " + id));
        CustomerDTO customerDTO = customerMapper.toDTO(customer);
        String imageURL = customerDTO.imageURL();
        if (imageURL != null && !imageURL.isEmpty()) {
            String tokenizedImageUrl = imageService.getImageUrlWithToken(imageURL);
            customerDTO = new CustomerDTO(
                    customerDTO.id(),
                    tokenizedImageUrl,
                    customerDTO.sexe(),
                    customerDTO.phone(),
                    customerDTO.userId()
            );
        }
        return customerDTO;
    }

    @Override
    public CustomerDTO create(UserDTO userDTO, CustomerDTO customerDTO, MultipartFile file) {
        if (userRepository.existsByEmail(userDTO.email())){
            throw new EmailAlreadyExistsException("Email already exist");
        }
        String imageUrl = null;
        if (file != null && !file.isEmpty()) {
            imageUrl = imageService.uploadFile(file);
        }
        CustomerDTO customerDTOWithImage = new CustomerDTO(
                null,
                imageUrl,
                customerDTO.sexe(),
                customerDTO.phone(),
                null
        );
        User userToSave = userMapper.toEntity(userDTO);
        userToSave.setPassword(passwordEncoder.encode(userDTO.password()));
        User savedUser = userRepository.save(userToSave);
        Customer customer = customerMapper.toEntity(customerDTOWithImage);
        customer.setUser(savedUser);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.toDTO(savedCustomer);
    }

    @Override
    public void updateCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.toEntity(customerDTO);
        Customer customer_saved = customerRepository.save(customer);
        customerMapper.toDTO(customer_saved);
    }
}
