package com.challenge.cargo.model;

import java.time.LocalDate;
import java.util.UUID;

public class BoosterRequest {
    private final String title;
    private final String requestedDate = LocalDate.now().toString();
    private final String uuid = UUID.randomUUID().toString();

    public BoosterRequest(String title) {
        this.title = title;
    }

    public String getRequestedDate() {
        return requestedDate;
    }

    public String getTitle() {
		return title;
	}

	public String getUuid() {
        return uuid;
    }
}
