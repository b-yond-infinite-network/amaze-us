package com.sourcecodelab.reminders.rest;

import com.sourcecodelab.reminders.rest.controller.RestControllerTestUtil;
import com.sourcecodelab.reminders.service.exceptions.ErrorCode;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class RestExceptionHandlerTest {

    private MockMvc mockMvc;
    @InjectMocks
    private RestControllerTestUtil restControllerTestUtil;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(restControllerTestUtil)
                .setControllerAdvice(new RestExceptionHandler())
                .build();
    }

    @Test
    public void handleReminderServiceException() throws Exception {
        mockMvc.perform(get("/test/reminder-service-exception"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value(ErrorCode.RESOURCE_NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.RESOURCE_NOT_FOUND.getMessage()));
    }
}