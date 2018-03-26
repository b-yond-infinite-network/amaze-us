package com.amertkara.udm.service.exception;

public class UserAlreadyExistException extends UDMException {
	private static final long serialVersionUID = -2936155975992218473L;

	public UserAlreadyExistException(ErrorPayload errorPayload) {
		super(errorPayload);
	}
}
