package com.sourcecodelab.mail.service.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    AWS_SES_ERROR("AWS_SNS_ERROR", "Error while sending mail via AWS SES"),
    INVALID_PAYLOAD("INVALID_PAYLOAD_ERROR", "The mail request payload is invalid");

    private String code;
    private String message;
}
