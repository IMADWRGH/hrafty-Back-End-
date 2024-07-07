package com.hrafty.web_app.Auth;


import com.hrafty.web_app.dto.UserDTO;


public class AuthenticationResponse {
    private String token;
    private UserDTO user;


    public AuthenticationResponse() {
    }

    public AuthenticationResponse(String token, UserDTO user) {
        this.token = token;
        this.user = new UserDTO(user.getId(), user.getFullName(), user.getEmail(),null, user.getRole());
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = new UserDTO(user.getId(), user.getFullName(), user.getEmail(), null,user.getRole());
    }
}
