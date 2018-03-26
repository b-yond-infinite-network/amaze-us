package com.amertkara.udm.service.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UDMException extends RuntimeException {
	private static final long serialVersionUID = 4108154816858924434L;

	private ErrorPayload errorPayload;
}
