package com.audela.challenge.busapi.exception;

import org.springframework.security.core.AuthenticationException;

public class BusAuthenticationException extends AuthenticationException {
    public BusAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public BusAuthenticationException(String msg) {
        super(msg);
    }
}
