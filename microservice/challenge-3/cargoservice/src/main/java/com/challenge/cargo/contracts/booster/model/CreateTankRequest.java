package com.challenge.cargo.contracts.booster.model;

import java.util.List;

public class CreateTankRequest {
    private String title;
    private boolean archived;
    private List<FuelPart> fuel;

    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}

	public List<FuelPart> getFuel() {
		return fuel;
	}

	public void setFuel(List<FuelPart> fuel) {
		this.fuel = fuel;
	}

	@Override
	public String toString() {
		return "CreateTankRequest{" +
				"title='" + title + '\'' +
				", archived=" + archived +
				", fuel=" + fuel +
				'}';
	}
}
