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

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<String> Exception(DuplicateResourceException msg){
        return new ResponseEntity<>(msg.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> Exception(BadRequestException msg){
        return new ResponseEntity<>(msg.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Add these missing handlers:

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> Exception(AuthenticationException msg){
        return new ResponseEntity<>(msg.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<String> Exception(ForbiddenException msg){
        return new ResponseEntity<>(msg.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<String> Exception(InvalidTokenException msg){
        return new ResponseEntity<>(msg.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ProfileIncompleteException.class)
    public ResponseEntity<java.util.Map<String, Object>> Exception(ProfileIncompleteException msg){
        java.util.Map<String, Object> body = new java.util.LinkedHashMap<>();
        body.put("error", "PROFILE_INCOMPLETE");
        body.put("message", msg.getMessage());
        body.put("userId", msg.getUserId());
        body.put("setupToken", msg.getSetupToken());
        return new ResponseEntity<>(body, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> Exception(ResourceNotFoundException msg){
        return new ResponseEntity<>(msg.getMessage(), HttpStatus.NOT_FOUND);
    }

}
