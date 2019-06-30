package com.challenge.booster.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

import com.challenge.booster.model.BoosterRequest;
import com.challenge.booster.model.BoosterResponse;
import com.challenge.booster.service.BoosterService;

/**
 * This class contains all Listeners in spring cloud stream bindings input
 *
 */
@Component
public class TankCreateConsumer {

	private static Logger LOGGER = LoggerFactory.getLogger(TankCreateConsumer.class);
    private final BoosterService boosterService;
    private final TankProducer tankCreateProducer;

    public TankCreateConsumer(BoosterService boosterService,
                               TankProducer tankCreateProducer) {
        this.boosterService = boosterService;
        this.tankCreateProducer = tankCreateProducer;
    }

    /**
     * A listener to consume a message which is posted in Spring Cloud Stream
     * @param boosterRequest {@link BoosterRequest}
     */
    @StreamListener(Sink.INPUT)
    public void consume(BoosterRequest boosterRequest) {
    	LOGGER.info("Consuming a message from rabbitMQ.");
        final BoosterResponse boosterResponse = boosterService.createTank(boosterRequest.getTitle());
        tankCreateProducer.publishTank(boosterResponse);
    }
}
