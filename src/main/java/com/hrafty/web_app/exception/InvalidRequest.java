package com.hrafty.web_app.exception;

public class InvalidRequest extends RuntimeException{
    public InvalidRequest(String msg){
        super(msg);
    }
}
