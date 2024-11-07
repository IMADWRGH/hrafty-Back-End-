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
    @ExceptionHandler(ServiceNotFoundException.class)
    public ResponseEntity<String> Exception(ServiceNotFoundException msg){
        return new ResponseEntity<>(msg.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> Exception(ProductNotFoundException msg){
        return new ResponseEntity<>(msg.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ImageNotFoundException.class)
    public ResponseEntity<String> Exception(ImageNotFoundException msg){
        return new ResponseEntity<>(msg.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> Exception(EmailAlreadyExistsException msg){
        return new ResponseEntity<>(msg.getMessage(),HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<String> Exception(InvalidPasswordException msg){
        return new ResponseEntity<>(msg.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
