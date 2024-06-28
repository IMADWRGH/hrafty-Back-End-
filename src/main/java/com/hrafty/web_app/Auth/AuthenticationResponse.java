package com.hrafty.web_app.Auth;





public class AuthenticationResponse {
    private String token;
    private Auth auth;

    public AuthenticationResponse() {}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }
}
