package com.challenge.booster;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.challenge.booster.controller.BoosterController;
import com.challenge.booster.model.BoosterResponse;
import com.challenge.booster.service.BoosterService;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

/**
 * This class is used to mock required objects in HTTP test scenarios. 
 * It is used with <baseClassMapping> tag in pom file 
 *
 */
public class BaseContractTest {
	
	private static Logger LOGGER = LoggerFactory.getLogger(BaseContractTest.class);

    /**
     * Called before all HTTP test scenarios
     */
    @Before
    public void setUp() {
    	LOGGER.info("Creating Mock objects...");
        final BoosterService mock = mock(BoosterService.class);
        when(mock.createTank("reverseTank")).thenReturn(new BoosterResponse("66ce29f3-ae87-4097-94e8-60b3b10c3855", "reverseTank"));
        when(mock.createTank("fakeTank")).thenReturn(new BoosterResponse("66ce29f3-ae87-4097-94e8-60b3b10c3855", "fakeTank"));
        RestAssuredMockMvc.standaloneSetup(new BoosterController(mock));
    }
}
