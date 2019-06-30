package com.challenge.booster.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.booster.model.BoosterRequest;
import com.challenge.booster.model.BoosterResponse;
import com.challenge.booster.service.BoosterService;

/**
 * A controller for incoming HTTP requests
 *
 */
@RestController
public class BoosterController {
    
    private static Logger LOGGER = LoggerFactory.getLogger(BoosterController.class);
    private final BoosterService boosterService;

    public BoosterController(BoosterService boosterService) {
        this.boosterService = boosterService;
    }

    /**
     * Create a tank 
     * @param boosterRequest {@link: BoosterRequest}
     * @return
     */
    @PostMapping("/tanks")
    public BoosterResponse createTank(@RequestBody BoosterRequest boosterRequest) {
    	LOGGER.info("Received a request to create a {} tank.", boosterRequest.getTitle());
        return boosterService.createTank(boosterRequest.getTitle());
    }
}
