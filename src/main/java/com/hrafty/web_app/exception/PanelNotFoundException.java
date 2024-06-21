package com.hrafty.web_app.exception;

public class PanelNotFoundException extends RuntimeException{
    public PanelNotFoundException(String msg){
        super(msg);
    }
}
