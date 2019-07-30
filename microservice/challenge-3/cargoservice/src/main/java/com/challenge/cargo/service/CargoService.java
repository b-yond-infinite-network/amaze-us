package com.challenge.cargo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.challenge.cargo.contracts.booster.gateway.BoosterGateway;
import com.challenge.cargo.contracts.booster.model.BoosterResponse;
import com.challenge.cargo.contracts.booster.model.CreateTankRequest;
import com.challenge.cargo.contracts.booster.model.CreateTankResponse;

@Component
public class CargoService {

	private static Logger LOGGER = LoggerFactory.getLogger(CargoService.class);
	private final BoosterGateway boosterGateway;

	public CargoService(BoosterGateway boosterGateway) {
		this.boosterGateway = boosterGateway;
	}

	public CreateTankResponse createBoosterTank(CreateTankRequest createTankRequest) {
		LOGGER.info("Sending a request through booster gateway to create a tank, {}", createTankRequest.toString());
		final BoosterResponse boosterResponse = boosterGateway.createBoosterTank(createTankRequest.getTitle(), createTankRequest.isArchived(), createTankRequest.getFuel());
		final String uuid = boosterResponse.getUuid();
		return new CreateTankResponse(uuid);
	}
}
