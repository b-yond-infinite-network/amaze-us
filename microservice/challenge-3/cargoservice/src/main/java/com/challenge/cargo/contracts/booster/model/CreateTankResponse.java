package com.challenge.cargo.contracts.booster.model;

public class CreateTankResponse {
    private final String uuid;

    public CreateTankResponse(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }
}
