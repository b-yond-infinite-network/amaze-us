package com.challenge.booster.service;

import com.challenge.booster.model.BoosterResponse;

/**
 * Contains all methods to interact with booster API
 *
 */
public interface BoosterService {
	
    public BoosterResponse createTank(String title);
}
