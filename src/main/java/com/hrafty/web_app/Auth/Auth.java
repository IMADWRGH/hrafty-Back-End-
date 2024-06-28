package com.hrafty.web_app.Auth;

import com.hrafty.web_app.dto.CustomerDTO;
import com.hrafty.web_app.dto.SellerDTO;
import com.hrafty.web_app.dto.UserDTO;
import lombok.Builder;

@Builder
public class Auth {
    private UserDTO user;
    private CustomerDTO customer;
    private SellerDTO seller;

    public Auth() {
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public SellerDTO getSeller() {
        return seller;
    }

    public void setSeller(SellerDTO seller) {
        this.seller = seller;
    }
}
