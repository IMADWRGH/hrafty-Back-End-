package com.hrafty.web_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ProfileIncompleteException extends RuntimeException {

    private final Long userId;
    private final String setupToken;

    public ProfileIncompleteException(String message, Long userId, String setupToken) {
        super(message);
        this.userId = userId;
        this.setupToken = setupToken;
    }

    public Long getUserId() { return userId; }
    public String getSetupToken() { return setupToken; }
}