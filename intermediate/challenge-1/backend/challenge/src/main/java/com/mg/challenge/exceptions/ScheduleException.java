package com.mg.challenge.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ScheduleException extends RuntimeException {
	private static final long serialVersionUID = 2874183601714286963L;

	public ScheduleException(String message) {
		super(message);
	}
}
