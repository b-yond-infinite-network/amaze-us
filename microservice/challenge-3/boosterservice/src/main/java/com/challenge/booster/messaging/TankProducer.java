package com.challenge.booster.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.challenge.booster.model.BoosterResponse;

/**
 * This class contains all publishers in spring cloud stream bindings output
 *
 */
@Component
public class TankProducer {
	

	private static Logger LOGGER = LoggerFactory.getLogger(TankProducer.class);
    private final Source source;

    public TankProducer(Source source) {
        this.source = source;
    }

    /**
     * A publisher to publish a message in Spring Cloud Stream
     * @param boosterResponse {@link BoosterResponse}
     */
    public void publishTank(BoosterResponse boosterResponse) {
    	LOGGER.info("Publishing a message to RabbitMQ, ", boosterResponse.toString());
        source.output().send(MessageBuilder.withPayload(boosterResponse).build());
    }
}
