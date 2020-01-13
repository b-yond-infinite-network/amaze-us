package com.eureka.timeline.controller;

import com.eureka.common.domain.Range;
import com.eureka.common.domain.dto.TweetDTO;
import com.eureka.timeline.service.RetwisRepository;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(loader= AnnotationConfigWebContextLoader.class)
public class TimelineControllerTests {
    private MockMvc mockMvc;

    private TimelineController timelineController;
    private RetwisRepository retwisRepository;


    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    protected WebApplicationContext wac;


    @Before
    public void setUp() throws Exception {
        mockMvc = webAppContextSetup(wac).alwaysExpect(status().isOk()).build();
        MockitoAnnotations.initMocks(this);

        retwisRepository = Mockito.mock(RetwisRepository.class);
        timelineController = new TimelineController(retwisRepository);

        List<TweetDTO> list = new ArrayList<>();
        TweetDTO dto = new TweetDTO();
        dto.setId(UUID.fromString("28738eb3-9a0a-4122-87a9-128597f796e9"));
        dto.setContent("Hellow world test");
        dto.setUserId(1L);

        TweetDTO dto1= new TweetDTO();
        dto1.setId(UUID.fromString("28738eb3-9a0a-4122-87a9-128597f796e8"));
        dto1.setContent("Hellow world test 2");
        dto1.setUserId(1L);

        Range range = new Range(1);

        list.add(dto);
        list.add(dto1);

        List<String> strings = new ArrayList<>();
        strings.add("28738eb3-9a0a-4122-87a9-128597f796e9");
        strings.add("28738eb3-9a0a-4122-87a9-128597f796e8");


        //Mockito.when(retwisRepository.getTweets(strings)).thenReturn(list);
        Mockito.when(retwisRepository.getTimeline("1", range)).thenReturn(list);
        Mockito.when(retwisRepository.getTimeline("2", range)).thenReturn(new ArrayList<>());
        Mockito.when(retwisRepository.hasMoreTimeline(eq("1"), (argThat((Range aBar) -> aBar.getPages() == 1L)))).thenReturn(true);
        Mockito.when(retwisRepository.hasMoreTimeline(eq("2"),  (argThat((Range aBar) -> aBar.getPages() == 2L)))).thenReturn(false);

        mockMvc = MockMvcBuilders.standaloneSetup(timelineController).build();

    }

    @Test
    public void testGetTweets() throws Exception {

        mockMvc.perform(get("/timeline/1")
                .contentType(APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk());
    }

    @Test
    public void testGetTweetsFail() throws Exception {

        mockMvc.perform(get("/timeline/2")
                .contentType(APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty() );
    }

    @Test
    public void testGetTimelineFail() throws Exception {

        MvcResult result = mockMvc.perform(get("/timeline/2/more")
                .contentType(APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk()).andReturn();

        boolean res = Boolean.parseBoolean(result.getResponse().getContentAsString());

        assertFalse(res);
    }

    @Test
    public void testGetTimeline() throws Exception {

        MvcResult result = mockMvc.perform(get("/timeline/1/more")
                .contentType(APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isOk()).andReturn();
        boolean res = Boolean.parseBoolean(result.getResponse().getContentAsString());

        assertTrue(res);
    }

}
