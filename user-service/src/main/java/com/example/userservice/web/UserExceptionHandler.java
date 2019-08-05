package com.example.userservice.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public void handleException() {
    }
}
