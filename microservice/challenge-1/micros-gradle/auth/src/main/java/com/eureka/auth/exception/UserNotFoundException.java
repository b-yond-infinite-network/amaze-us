package com.eureka.auth.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 *  UserNotFoundException class.
 *
 * @author Miguel Gonzalez (maggonzz@gmail.com)
 * @since 0.0.1
 */
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