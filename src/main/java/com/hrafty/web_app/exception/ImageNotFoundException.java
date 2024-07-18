package com.hrafty.web_app.exception;

public class ImageNotFoundException extends RuntimeException{
    public ImageNotFoundException(String msg,Throwable cause){
        super(msg,cause);
    }
}
