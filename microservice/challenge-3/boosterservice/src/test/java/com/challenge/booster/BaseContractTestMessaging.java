package com.challenge.booster;

import javax.annotation.PostConstruct;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.challenge.booster.messaging.TankProducer;
import com.challenge.booster.model.BoosterResponse;
import com.challenge.booster.service.BoosterService;

/**
 * This class is used to mock required objects in messaging test scenarios. 
 * It is used with <baseClassMapping> tag in pom file 
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {BoosterServiceApplication.class,
        BaseContractTestMessaging.TestConfiguration.class})
@AutoConfigureMessageVerifier
public class BaseContractTestMessaging {
	
	private static Logger LOGGER = LoggerFactory.getLogger(BaseContractTestMessaging.class);

    @Autowired
    private TankProducer tankProducer;

    @Autowired
    private BoosterService boosterService;

    /**
     * This method is used by triggeredBy method in Groovy test as an input in Messaging
     */
    public void tankCreator() {
    	LOGGER.info("Message trigger has been fired...");
        final String uuid = "e2a8b899-6b62-4010-81f1-9faed24fed2b";
        tankProducer.publishTank(new BoosterResponse(uuid, "reverseTank"));
    }

    @PostConstruct
    public void postConstruct() {
        Mockito.when(boosterService.createTank("reverseTank"))
                .thenReturn(new BoosterResponse("e2a8b899-6b62-4010-81f1-9faed24fed2b", "reverseTank"));
    }

    @Configuration
    public static class TestConfiguration {

        @MockBean
        private BoosterService boosterService;
    }
}
