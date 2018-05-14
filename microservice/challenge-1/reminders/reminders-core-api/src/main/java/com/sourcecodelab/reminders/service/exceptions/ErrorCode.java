package com.sourcecodelab.reminders.service.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND", "Error while getting a reminder."),
    INVALID_PAYLOAD("INVALID_PAYLOAD_ERROR", "The mail request payload is invalid");

    private String code;
    private String message;
}
