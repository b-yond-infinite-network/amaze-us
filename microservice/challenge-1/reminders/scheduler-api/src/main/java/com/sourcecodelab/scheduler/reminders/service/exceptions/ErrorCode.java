package com.sourcecodelab.scheduler.reminders.service.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    SCHEDULER_ERROR("SCHEDULER_ERROR", "Error while scheduling the reminder"),
    INVALID_PAYLOAD("INVALID_PAYLOAD_ERROR", "The mail request payload is invalid");

    private String code;
    private String message;
}
