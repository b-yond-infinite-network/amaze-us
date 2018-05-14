package com.sourcecodelab.reminders.service.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ReminderServiceException extends RuntimeException {
    private String code;
    private String message;

    public ReminderServiceException (ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
}
