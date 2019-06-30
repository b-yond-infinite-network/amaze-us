package com.challenge.cargo.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.challenge.cargo.model.BoosterRequest;

/**
 * A producer in input-outout test scenarios
 *
 */
@Component
public class BoosterTankProducer {

	private static Logger LOGGER = LoggerFactory.getLogger(BoosterTankProducer.class);
    private final Source source;

    public BoosterTankProducer(Source source) {
        this.source = source;
    }

    public void requestTank(BoosterRequest boosterRequest) {
    	LOGGER.info("Sending a message to spring.cloud.stream.bindings.output.destination");
        source.output().send(MessageBuilder.withPayload(boosterRequest).build());
    }
}
