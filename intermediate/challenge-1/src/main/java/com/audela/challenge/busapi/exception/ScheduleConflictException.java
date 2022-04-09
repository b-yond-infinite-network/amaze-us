package com.audela.challenge.busapi.exception;

public class ScheduleConflictException extends RuntimeException{
    public ScheduleConflictException() {
        super();
    }
    public ScheduleConflictException(String message, Throwable cause) {
        super(message, cause);
    }
    public ScheduleConflictException(String message) {
        super(message);
    }
    public ScheduleConflictException(Throwable cause) {
        super(cause);
    }
}
