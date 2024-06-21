package com.hrafty.web_app.exception;

public class EmailNotFoundException extends RuntimeException {

    public EmailNotFoundException(String msg){
        super(msg);
    }
}
