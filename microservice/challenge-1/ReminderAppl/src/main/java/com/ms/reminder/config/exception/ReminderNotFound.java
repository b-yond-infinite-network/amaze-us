package com.ms.reminder.config.exception;

import org.springframework.http.HttpStatus;

public class ReminderNotFound extends Exception {
	
	
	private static final long serialVersionUID = 1L;
	private HttpStatus httpStatus;
	private String message;
	private String error;
	
	public ReminderNotFound(HttpStatus httpStatus, String message, String error) {
		super();
		this.httpStatus = httpStatus;
		this.message = message;
		this.error = error;
	}
	
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public String getError() {
		return error;
	}

}
