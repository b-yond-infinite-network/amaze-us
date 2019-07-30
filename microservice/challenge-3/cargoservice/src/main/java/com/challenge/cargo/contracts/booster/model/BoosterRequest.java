package com.challenge.cargo.contracts.booster.model;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class BoosterRequest {
    private final String title;
    private final String requestedDate = LocalDate.now().toString();
    private final String uuid = UUID.randomUUID().toString();
    private final boolean archived;
    private List<FuelPart> fuel;

    public BoosterRequest(String title, boolean archived, List<FuelPart> fuel) {
        this.title = title;
        this.archived=archived;
        this.fuel = fuel;
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

    public boolean isArchived() {
        return archived;
    }

    public List<FuelPart> getFuel() {
        return fuel;
    }

    public void setFuel(List<FuelPart> fuel) {
        this.fuel = fuel;
    }
}
