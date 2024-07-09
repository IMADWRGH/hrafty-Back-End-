package com.hrafty.web_app.exception;

public class ProductNotFoundException  extends RuntimeException{
    public ProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
