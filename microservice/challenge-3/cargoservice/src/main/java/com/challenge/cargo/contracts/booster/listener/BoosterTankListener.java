package com.challenge.cargo.contracts.booster.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

import com.challenge.cargo.contracts.booster.model.BoosterResponse;
import com.challenge.cargo.contracts.booster.repository.BoosterTankRepository;

/**
 * A Listener for Input-Output test scenarios
 *
 */
@Component
public class BoosterTankListener {

	private static Logger LOGGER = LoggerFactory.getLogger(BoosterTankListener.class);
    private final BoosterTankRepository boosterTankRepository;

    public BoosterTankListener(BoosterTankRepository repository) {
        this.boosterTankRepository = repository;
    }

    @StreamListener(Sink.INPUT)
    public void receiveBoosterResponse(BoosterResponse boosterResponse) {
    	LOGGER.info("Recieved a message from spring.cloud.stream.bindings");
        boosterTankRepository.save(boosterResponse.getUuid(), boosterResponse.getTitle());
    }
}
