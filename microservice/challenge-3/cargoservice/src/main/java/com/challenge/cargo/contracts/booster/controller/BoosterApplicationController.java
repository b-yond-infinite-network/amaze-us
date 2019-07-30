package com.challenge.cargo.contracts.booster.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.cargo.contracts.booster.model.CreateTankRequest;
import com.challenge.cargo.contracts.booster.model.CreateTankResponse;
import com.challenge.cargo.service.CargoService;

/**
 * A controller to interact with booster application in HTTP tests between cargo and booster
 *
 */
@RestController
public class BoosterApplicationController {
	
	private static Logger LOGGER = LoggerFactory.getLogger(BoosterApplicationController.class);

    private final CargoService cargoService;

    public BoosterApplicationController(CargoService creditCheckService) {
        this.cargoService = creditCheckService;
    }

    /**
     * Booster application POST mapping handler
     * @param createTankRequest {@link CreateTankRequest}
     * @return {@link CreateTankResponse}
     */
    @PostMapping(value = "/booster-application", consumes = MediaType.APPLICATION_JSON_VALUE)
    public CreateTankResponse createBoosterTank(@RequestBody final CreateTankRequest createTankRequest) {
    	LOGGER.info("Received an HTTP request, {}", createTankRequest.toString());
        return cargoService.createBoosterTank(createTankRequest);
    }
}
