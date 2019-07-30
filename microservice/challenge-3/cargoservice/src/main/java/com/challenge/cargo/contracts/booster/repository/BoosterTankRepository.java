package com.challenge.cargo.contracts.booster.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * In memory repository using hashMap for Messaging test scenarios
 *
 */
@Component
public class BoosterTankRepository {

	private static Logger LOGGER = LoggerFactory.getLogger(BoosterTankRepository.class);
    private final Map<String, String> boosterTankMap = new HashMap<>();

    public String getTitle(UUID uuid) {
    	LOGGER.info("Finding a booster title with '{}' id in repository map with '{}' size."
    			, uuid, boosterTankMap.size());
        return boosterTankMap.get(uuid.toString());
    }

    public void save(String uuid, String title) {
    	LOGGER.info("Saving '{}' in repository.", title);
        boosterTankMap.put(uuid, title);
    }
}
