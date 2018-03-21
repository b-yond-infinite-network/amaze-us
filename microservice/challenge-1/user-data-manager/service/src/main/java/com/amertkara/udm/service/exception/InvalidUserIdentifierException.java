package com.amertkara.udm.service.exception;

public class InvalidUserIdentifierException extends UDMException {
	private static final long serialVersionUID = 7089644467188974963L;

	public InvalidUserIdentifierException(ErrorPayload errorPayload) {
		super(errorPayload);
	}
}
