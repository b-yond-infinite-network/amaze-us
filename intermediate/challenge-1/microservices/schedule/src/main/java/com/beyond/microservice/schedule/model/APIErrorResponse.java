package com.beyond.microservice.schedule.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * {@code APIErrorResponse} class containing error code, error type and error message. This
 * object returns to the client on any or every error encountered in the service.
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class APIErrorResponse {
    
    private int errorCode;
    
    private ErrorType errorType;
    
    private String errorMessage;
    
    /**
     * This is the error type constants. Use relevant error type while constructing the error
     * response. Add additional error type if the existing does not suffice the need.
     */
    public enum ErrorType {
        CLIENT_ERROR,
        SERVER_ERROR
    }
    
}
