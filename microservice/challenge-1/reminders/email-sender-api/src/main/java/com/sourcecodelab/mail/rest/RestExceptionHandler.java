package com.sourcecodelab.mail.rest;

import com.sourcecodelab.mail.service.exceptions.MailServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.sourcecodelab.mail.service.exceptions.ErrorCode.INVALID_PAYLOAD;

@Slf4j
@AllArgsConstructor
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = {MailServiceException.class})
	protected ResponseEntity<Object> handleMailServiceException(MailServiceException e, WebRequest request) {
		log.error("Received Mail service Exception code={} and message={}", e.getCode(), e.getMessage());
		return handleExceptionInternal(e, e, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Received malformed JSON payload requestBody.", e);
        MailServiceException exception = new MailServiceException(INVALID_PAYLOAD);
        return handleExceptionInternal(e, exception, headers, status, request);
    }
}
