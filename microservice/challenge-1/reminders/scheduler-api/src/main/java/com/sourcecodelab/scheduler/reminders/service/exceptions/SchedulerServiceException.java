package com.sourcecodelab.scheduler.reminders.service.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SchedulerServiceException extends RuntimeException {
    private String code;
    private String message;

    public SchedulerServiceException(ErrorCode error) {
        this.code = error.getCode();
        this.message = error.getMessage();
    }
}
