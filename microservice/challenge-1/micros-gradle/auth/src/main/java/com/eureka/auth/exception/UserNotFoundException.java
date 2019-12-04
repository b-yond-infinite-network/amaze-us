package com.eureka.auth.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(){
        super(NOT_FOUND.getReasonPhrase());
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}