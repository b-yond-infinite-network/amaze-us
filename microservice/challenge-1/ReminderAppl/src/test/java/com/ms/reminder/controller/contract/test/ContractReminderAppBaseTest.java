package com.ms.reminder.controller.contract.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;
import com.ms.reminder.controller.AppController;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT,properties="classpath:application-test.yml")
@Profile("test")
public abstract class ContractReminderAppBaseTest {
	
	 @Autowired
	 private AppController appController;
	  
	  @BeforeEach
	  public void setup() {
		  StandaloneMockMvcBuilder standaloneMockMvcBuilder 
          = MockMvcBuilders.standaloneSetup(appController);
        RestAssuredMockMvc.standaloneSetup(standaloneMockMvcBuilder);
	  }

}
