package com.beyond.microservice.schedule.exception;


import com.beyond.microservice.schedule.model.APIErrorResponse;
import com.beyond.microservice.schedule.model.APIErrorResponse.ErrorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Global exception handler class which intercept all exceptions and prepare error response before
 * sending it back to the client.
 * <p>
 * Note: Add new kind of exceptions to this class when the existing exception methods does not fit
 * or overridden behavior is required.
 * </p>
 */
@RestControllerAdvice
@Slf4j
public class APIExceptionHandler extends ResponseEntityExceptionHandler {
    
    private APIErrorResponse buildResponse(final HttpStatus status,
                                           final ErrorType errorType,
                                           final String message) {
        final APIErrorResponse errorResponse = APIErrorResponse.builder()
                                                               .errorCode(status.value())
                                                               .errorType(errorType)
                                                               .errorMessage(message)
                                                               .build();
        log.info(errorResponse.toString());
        return errorResponse;
    }
    
    /**
     * Handle {@code ResourceNotFoundException} exception
     *
     * @param exception the exception object
     * @return the API error response object
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIErrorResponse> handleResourceNotFound(
        final ResourceNotFoundException exception) {
        final APIErrorResponse errorResponse = buildResponse(HttpStatus.NOT_FOUND,
                                                             ErrorType.CLIENT_ERROR,
                                                             exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(errorResponse);
    }
    
    /**
     * Handle {@code NullPointerException} exception
     *
     * @param exception the exception object
     * @return the API error response object
     */
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<APIErrorResponse> handleNullPointerException(
        final NullPointerException exception) {
        final APIErrorResponse errorResponse = buildResponse(HttpStatus.NOT_FOUND,
                                                             ErrorType.CLIENT_ERROR,
                                                             exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(errorResponse);
    }
    
    /**
     * Handle {@code Exception} exception, parent of all exception classes
     *
     * @param exception the exception object
     * @return the API error response object
     */
    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<APIErrorResponse> handleAllOther(final Exception exception) {
        final APIErrorResponse errorResponse = buildResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                                                             ErrorType.SERVER_ERROR,
                                                             exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(errorResponse);
    }
    
}
