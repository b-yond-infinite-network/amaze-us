package com.eureka.fanout.controller;

import com.eureka.common.domain.dto.TweetDTO;
import com.eureka.fanout.service.FanoutService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(loader= AnnotationConfigWebContextLoader.class)
public class FanoutControllerTests {


    private MockMvc mockMvc;
    private FanoutService fanoutService;
    private FanoutController fanoutController;
    @Autowired
    protected WebApplicationContext wac;
    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Before
    public void setUp() throws Exception {
        mockMvc = webAppContextSetup(wac).alwaysExpect(status().isOk()).build();
        MockitoAnnotations.initMocks(this);

        fanoutService= Mockito.mock(FanoutService.class);
        fanoutController = new FanoutController(fanoutService);

        TweetDTO dto = new TweetDTO();
        dto.setId(UUID.fromString("28738eb3-9a0a-4122-87a9-128597f796e9"));
        dto.setContent("Hellow world test");
        dto.setUserId(1L);


        doNothing().when(fanoutService).fanout(argThat((TweetDTO aBar) -> aBar.getUserId() == 1L && aBar.getContent().equals("Hello world test")));

        mockMvc = MockMvcBuilders.standaloneSetup(fanoutController).build();

    }

    @Test
    public void testFindTweets() throws Exception {


        TweetDTO dto = new TweetDTO();
        dto.setId(UUID.fromString("28738eb3-9a0a-4122-87a9-128597f796e9"));
        dto.setContent("Hellow world test");
        dto.setUserId(1L);

        mockMvc.perform(post("/fanout")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(dto))

        )
                .andExpect(status().isOk());
    }


    public  byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}
