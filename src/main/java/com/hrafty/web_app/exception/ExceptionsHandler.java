package com.hrafty.web_app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.management.relation.RoleNotFoundException;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<String> Exception(EmailNotFoundException msg){
        return new ResponseEntity<>(msg.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<String> Exception(RoleNotFoundException msg){
        return new ResponseEntity<>(msg.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidRequest.class)
    public ResponseEntity<String> Exception(InvalidRequest msg){
        return new ResponseEntity<>(msg.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PanelNotFoundException.class)
    public ResponseEntity<String> Exception(PanelNotFoundException msg){
        return new ResponseEntity<>(msg.getMessage(), HttpStatus.NOT_FOUND);
    }
}
