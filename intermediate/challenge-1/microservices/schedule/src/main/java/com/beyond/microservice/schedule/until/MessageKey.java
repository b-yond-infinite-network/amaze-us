package com.beyond.microservice.schedule.until;

public enum MessageKey {
    SCHEDULE_NOT_FOUND("schedule.not.found"),
    SCHEDULE_ID_IS_NULL("schedule-id.is.null"),;
    private final String key;
    
    public String value() {
        return key;
    }
    
    MessageKey(final String key) {
        this.key = key;
    }
}
