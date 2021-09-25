package com.mg.challenge.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SchedulePKException extends RuntimeException {
	private static final long serialVersionUID = 2874183601714286963L;

	public SchedulePKException(String message) {
		super(message);
	}
}
