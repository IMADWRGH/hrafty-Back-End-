package com.hrafty.web_app.Auth;

import com.hrafty.web_app.dto.CustomerDTO;
import com.hrafty.web_app.dto.UserDTO;

public class RequestCustomer {
    private UserDTO userDTO;
    private CustomerDTO customerDTO;

    public RequestCustomer(UserDTO userDTO, CustomerDTO customerDTO) {
        this.userDTO = userDTO;
        this.customerDTO = customerDTO;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public CustomerDTO getCustomerDTO() {
        return customerDTO;
    }

    public void setCustomerDTO(CustomerDTO customerDTO) {
        this.customerDTO = customerDTO;
    }
}
