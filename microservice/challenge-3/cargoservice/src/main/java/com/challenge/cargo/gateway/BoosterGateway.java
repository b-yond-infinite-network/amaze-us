package com.challenge.cargo.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.challenge.cargo.model.BoosterRequest;
import com.challenge.cargo.model.BoosterResponse;

/**
 * A Gateway to send http requests and response to Booster service
 *
 */
@Component
public class BoosterGateway {

	private static Logger LOGGER = LoggerFactory.getLogger(BoosterGateway.class);
    private final RestTemplate restTemplate;
    private final String boosterServiceBaseurl;

    public BoosterGateway(RestTemplate restTemplate,
                                            @Value("${booster.service.baseurl}") String boosterServiceBaseurl) {
        this.restTemplate = restTemplate;
        this.boosterServiceBaseurl = boosterServiceBaseurl;
    }

    /**
     * Sending a tank request to creating a new Tank
     * @param title
     * @return
     */
    public BoosterResponse createBoosterTank(String title) {
    	LOGGER.info("Sending an http request to booster service to create a tank.");
        final String uri = UriComponentsBuilder.fromHttpUrl(boosterServiceBaseurl)
                .path("tanks")
                .toUriString();

        final BoosterRequest request = new BoosterRequest(title);
        final BoosterResponse boosterResponse = restTemplate.postForObject(uri, request, BoosterResponse.class);

        if (!boosterResponse.getUuid().equals(request.getUuid())) {
            throw new RuntimeException("If these don't match something horrible happens");
        }

        LOGGER.info("Receviced a response from booster contract, ", boosterResponse.toString());
        return boosterResponse;
    }

}
