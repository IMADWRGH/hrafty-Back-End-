package com.hrafty.web_app.Auth;

import com.hrafty.web_app.dto.CustomerDTO;
import com.hrafty.web_app.dto.SellerDTO;
import com.hrafty.web_app.dto.UserDTO;

public class AuthDTO {
    private UserDTO user;
    private SellerDTO seller;
    private CustomerDTO customer;

    public AuthDTO() {}

    public AuthDTO(UserDTO user, SellerDTO seller, CustomerDTO customer) {
        this.user = user;
        this.seller = seller;
        this.customer = customer;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public SellerDTO getSeller() {
        return seller;
    }

    public void setSeller(SellerDTO seller) {
        this.seller = seller;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "AuthResponseDTO{" +
                "user=" + user +
                ", seller=" + seller +
                ", customer=" + customer +
                '}';
    }
}
