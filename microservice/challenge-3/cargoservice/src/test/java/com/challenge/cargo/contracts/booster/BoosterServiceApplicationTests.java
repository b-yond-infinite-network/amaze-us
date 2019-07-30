package com.challenge.cargo.contracts.booster;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Http tests
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureStubRunner(ids = "com.challenge:booster-service:+:stubs:8080", workOffline = true)
public class BoosterServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldCreateReverseTank() throws Exception {
        mockMvc.perform(
                post("/booster-application")
                        .contentType(APPLICATION_JSON)
                        .content("{" +
                                "\"title\": \"reverseTank\"," +
                                "\"archived\": true," +
                                "\"fuel\": [{\"title\":\"part1\", \"priority\":\"1\", \"done\": true, \"deadline\":\"2020-01-02T15:04:05Z\"}]" +
                                "}"
                        ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid", is(notNullValue())))
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
    }

    @Test
    public void shouldCreateFakeTank() throws Exception {
        mockMvc.perform(
                post("/booster-application")
                        .contentType(APPLICATION_JSON)
                        .content("{" +
                        		"\"title\": \"fakeTank\"," +
                                "\"archived\": true," +
                                "\"fuel\": [{\"title\":\"part1\", \"priority\":\"1\", \"done\": true, \"deadline\":\"2020-01-02T15:04:05Z\"}]" +
                                "}"
                        ))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid", is(notNullValue())))
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON));
    }
}
