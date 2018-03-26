package com.amertkara.udm.exception;

import static com.amertkara.udm.service.exception.ErrorCode.UNKNOWN_ERROR;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.amertkara.udm.service.exception.ErrorPayload;
import com.amertkara.udm.service.exception.InvalidUserDataException;
import com.amertkara.udm.service.exception.InvalidUserIdentifierException;
import com.amertkara.udm.service.exception.UDMException;
import com.amertkara.udm.service.exception.UserAlreadyExistException;
import com.amertkara.udm.service.exception.UserNotFoundException;

@Slf4j
@ControllerAdvice
public class UDMExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(UserAlreadyExistException.class)
	protected ResponseEntity<Object> handleConflict(UDMException e, WebRequest webRequest) {
		log.error("User already exists exception occurred, errorPayload={}", e.getErrorPayload());
		return handleExceptionInternal(e, e.getErrorPayload(), new HttpHeaders(), CONFLICT, webRequest);
	}

	@ExceptionHandler(UserNotFoundException.class)
	protected ResponseEntity<Object> handleNotFound(UDMException e, WebRequest webRequest) {
		log.error("User not found exception occurred, errorPayload={}", e.getErrorPayload());
		return handleExceptionInternal(e, e.getErrorPayload(), new HttpHeaders(), NOT_FOUND, webRequest);
	}

	@ExceptionHandler({InvalidUserDataException.class, InvalidUserIdentifierException.class})
	protected ResponseEntity<Object> handleBadRequest(UDMException e, WebRequest webRequest) {
		// TODO: improve the payload to include invalid fields
		log.error("User data is not valid, errorPayload={}", e.getErrorPayload());
		return handleExceptionInternal(e, e.getErrorPayload(), new HttpHeaders(), BAD_REQUEST, webRequest);
	}

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleInternalServerError(Exception e, WebRequest webRequest) {
		ErrorPayload errorPayload = ErrorPayload.builder().code(UNKNOWN_ERROR).build();
		log.error("Unknown exception occured", e);
		return handleExceptionInternal(e, errorPayload, new HttpHeaders(), BAD_REQUEST, webRequest);
	}
}
