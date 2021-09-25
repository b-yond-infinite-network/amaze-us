package com.mg.challenge.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler
	public final ResponseEntity<Object> handleDriverSSNException(DriverSSNException ex, WebRequest request) {
		DriverSSNExceptionResponse response = new DriverSSNExceptionResponse(ex.getMessage());
		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public final ResponseEntity<Object> handleBusIDException(BusIDException ex, WebRequest request) {
		BusIDExceptionResponse response = new BusIDExceptionResponse(ex.getMessage());
		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public final ResponseEntity<Object> handleSchedulePKException(SchedulePKException ex, WebRequest request) {
		SchedulePKExceptionResponse response = new SchedulePKExceptionResponse(ex.getMessage());
		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
	}
}
