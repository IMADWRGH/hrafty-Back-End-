package com.hrafty.web_app.exception;

public class ServiceNotFoundException extends RuntimeException{
    public ServiceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceNotFoundException(String msg) {
        super(msg);
    }
}
