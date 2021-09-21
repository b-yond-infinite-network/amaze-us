package com.mg.challenge.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BusIDException extends RuntimeException {
	private static final long serialVersionUID = -9032428644601645543L;

	public BusIDException(String message) {
		super(message);
	}
}
