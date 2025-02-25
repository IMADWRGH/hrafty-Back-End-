package com.hrafty.web_app.Auth;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrafty.web_app.Auth.Service.AuthService;
import com.hrafty.web_app.dto.CustomerDTO;
import com.hrafty.web_app.dto.SellerDTO;
import com.hrafty.web_app.dto.UserDTO;
import com.hrafty.web_app.exception.EmailAlreadyExistsException;
import com.hrafty.web_app.exception.EmailNotFoundException;
import com.hrafty.web_app.exception.InvalidPasswordException;
import com.hrafty.web_app.services.CustomerService;
import com.hrafty.web_app.services.SellerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "api/v1/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {
    private final AuthService authService;
    private final SellerService sellerService;
    private final CustomerService customerService;

    public AuthenticationController(AuthService authService, SellerService sellerService, CustomerService customerService) {
        this.authService = authService;
        this.sellerService = sellerService;
        this.customerService = customerService;
    }


    @PostMapping(path = "/register-seller",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> registerSeller(@RequestPart("user") String userJson,
                                                 @RequestPart("seller") String sellerJson,
                                                 @RequestPart(value = "file", required = false) MultipartFile file) throws JsonProcessingException {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            UserDTO userDTO = objectMapper.readValue(userJson, UserDTO.class);
            SellerDTO sellerDTO = objectMapper.readValue(sellerJson, SellerDTO.class);
            SellerDTO savedSellerDTO=sellerService.create(userDTO,sellerDTO,file);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedSellerDTO);
        }catch (EmailAlreadyExistsException msg){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(msg.getMessage());
        }
    }

    @PostMapping(path = "/register-customer",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> registerCustomer( @RequestPart("user") String userJson,
                                          @RequestPart("customer") String customerJson,
                                          @RequestPart(value = "file", required = false) MultipartFile file) throws JsonProcessingException {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            UserDTO userDTO = objectMapper.readValue(userJson, UserDTO.class);
            CustomerDTO customerDTO = objectMapper.readValue(customerJson, CustomerDTO.class);
            CustomerDTO savedCustomerDTO = customerService.create(userDTO, customerDTO, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomerDTO);
        }catch (EmailAlreadyExistsException msg){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(msg.getMessage());
        }
    }
    @PostMapping(path = "/login")
    public ResponseEntity<Object> login(@RequestBody AuthenticationRequest request) {
        try {
            AuthenticationResponse response = authService.login(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(response);
        } catch (EmailNotFoundException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        } catch (InvalidPasswordException msg) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(msg.getMessage());
        }
    }


}
