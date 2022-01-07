package com.ms.reminder.integration.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms.reminder.model.Reminder;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,properties = "spring.profiles.active:test")
@TestPropertySource(locations = { "classpath:application-test.yml" })
public class ReminderAppIntegrationTests {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReminderAppIntegrationTests.class);
	@LocalServerPort
    private int port;
 
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
   
   
    
    
    @Test
    public void testfindReminder() throws JsonProcessingException, Exception 
    {
       Reminder reminderOne=Reminder.builder()
    			.name("Reminder-1")
    			.isComplete(false)
    			.date(LocalDateTime.now())
    			.build();
    	
    	mockMvc.perform(post("/v1/reminderapp")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(reminderOne)))
                .andExpect(status().isCreated());
    	
    	mockMvc.perform( MockMvcRequestBuilders
  		      .get("/v1/reminderapp/{id}", 1)
  		      .accept(MediaType.APPLICATION_JSON))
  		      .andDo(print())
  		      .andExpect(status().isFound())
  		      .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    	
    	
    }
    
    @Test
    void testfindAllReminders() throws Exception {
    	
    	Reminder reminderOne=Reminder.builder()
    			.name("Reminder-2")
    			.isComplete(false)
    			.date(LocalDateTime.now())
    			.build();
    	
    	mockMvc.perform(post("/v1/reminderapp")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(reminderOne)))
                .andExpect(status().isCreated());
    	
    	Reminder reminderTwo=Reminder.builder()
    			.name("Reminder-3")
    			.isComplete(false)
    			.date(LocalDateTime.now())
    			.build();

        mockMvc.perform(post("/v1/reminderapp")
              .contentType("application/json")
              .content(objectMapper.writeValueAsString(reminderTwo)))
              .andExpect(status().isCreated());
     
    	mockMvc.perform( MockMvcRequestBuilders
  		      .get("/v1/reminderapp")
  		      .accept(MediaType.APPLICATION_JSON))
  		      .andDo(print())
  		      .andExpect(status().isFound())
  		      .andExpect(MockMvcResultMatchers.jsonPath("$.[*].id").isNotEmpty());
      
    }


}
