package com.challenge.booster.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.challenge.booster.model.BoosterResponse;

@Component
public class BoosterServiceImpl implements BoosterService {
	
	private static Logger LOGGER = LoggerFactory.getLogger(BoosterServiceImpl.class);
	
	@Override
    public BoosterResponse createTank(String title) {
    	LOGGER.info("creating a booster tank with {} title.", title);
        return null;
    }
}
