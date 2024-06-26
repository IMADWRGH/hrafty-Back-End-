package com.hrafty.web_app.exception;

public class InvalidPasswordException extends RuntimeException{
    public InvalidPasswordException(String msg){
        super(msg);
    }
}
