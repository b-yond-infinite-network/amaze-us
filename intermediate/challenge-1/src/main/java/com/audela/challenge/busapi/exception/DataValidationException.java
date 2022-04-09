package com.audela.challenge.busapi.exception;

public class DataValidationException extends RuntimeException{
    public DataValidationException() {
        super();
    }
    public DataValidationException(String message, Throwable cause) {
        super(message, cause);
    }
    public DataValidationException(String message) {
        super(message);
    }
    public DataValidationException(Throwable cause) {
        super(cause);
    }
}
