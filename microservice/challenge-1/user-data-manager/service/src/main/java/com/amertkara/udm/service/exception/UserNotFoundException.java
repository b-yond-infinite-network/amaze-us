package com.amertkara.udm.service.exception;

public class UserNotFoundException extends UDMException {
	private static final long serialVersionUID = 1539068735470803797L;

	public UserNotFoundException(ErrorPayload errorPayload) {
		super(errorPayload);
	}
}
