package com.challenge.booster.model;

import java.util.List;

/**
 * Contains all booster fields in request
 *
 */
public class BoosterRequest {
    private String title;
	private FuelPart fuel;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public FuelPart getFuel() {
		return fuel;
	}

	public void setFuel(FuelPart fuel) {
		this.fuel = fuel;
	}
}
