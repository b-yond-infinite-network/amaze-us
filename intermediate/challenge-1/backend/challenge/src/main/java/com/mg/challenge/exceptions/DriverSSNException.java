package com.mg.challenge.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DriverSSNException extends RuntimeException {
	private static final long serialVersionUID = 3191059864986361164L;

	public DriverSSNException(String message) {
		super(message);
	}
}
