package com.amertkara.udm.service.exception;

public class InvalidUserDataException extends UDMException {
	private static final long serialVersionUID = 2215659749652407604L;

	public InvalidUserDataException(ErrorPayload errorPayload) {
		super(errorPayload);
	}
}
