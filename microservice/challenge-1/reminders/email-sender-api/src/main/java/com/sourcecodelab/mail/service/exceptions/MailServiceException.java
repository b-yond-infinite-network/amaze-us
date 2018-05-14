package com.sourcecodelab.mail.service.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class MailServiceException extends RuntimeException {
    private String code;
    private String message;

    public MailServiceException (ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
}
