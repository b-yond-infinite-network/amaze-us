package com.eureka.tweet.controller;

import com.eureka.common.domain.dto.SignUpRequest;
import com.eureka.common.domain.dto.TweetList;
import com.eureka.tweet.domain.Mention;
import com.eureka.tweet.domain.Tweet;
import com.eureka.tweet.service.MentionService;
import com.eureka.tweet.service.TweetService;
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
import org.springframework.web.util.NestedServletException;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.argThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(loader= AnnotationConfigWebContextLoader.class)
public class TweetControllerTests {

    private MockMvc mockMvc;

    private TweetService tweetService;
    private MentionService mentionService;
    private TweetController tweetController;

    private static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @Autowired
    protected WebApplicationContext wac;
    SignUpRequest payload;

    @Before
    public void setUp() throws Exception {

        mockMvc = webAppContextSetup(wac).alwaysExpect(status().isOk()).build();
        MockitoAnnotations.initMocks(this);

        tweetService= Mockito.mock(TweetService.class);
        mentionService= Mockito.mock(MentionService.class);

        tweetController=new TweetController(tweetService,mentionService);

        UUID uid = UUID.fromString("f0d12829-cb9b-4be9-837d-d75e931ba899");

        Tweet tweet = new Tweet();
        tweet.setUserId(1L);
        tweet.setContent("Hello world test");
        tweet.setId(uid);


        Tweet tweet1 = new Tweet();
        tweet1.setUserId(1L);
        tweet1.setContent("Hello world test 2");
        tweet1.setId(UUID.fromString("00d12829-cb9b-4be9-837d-d75e931ba899"));


        List<Tweet> tweets = new ArrayList<>();
        tweets.add(tweet);
        tweets.add(tweet1);


        List<String> list = new ArrayList<>();
        list.add("f0d12829-cb9b-4be9-837d-d75e931ba899");
        list.add("00d12829-cb9b-4be9-837d-d75e931ba899");

        Mockito.when(tweetService.createTweet(argThat((Tweet aBar) -> aBar.getUserId() == 1L && aBar.getContent().equals("Hello world test")))).thenReturn(java.util.Optional.of(tweet));

        Mockito.when(tweetService.findTweetById("f0d12829-cb9b-4be9-837d-d75e931ba899")).thenReturn(Optional.of(tweet));

        Mockito.when(tweetService.findTweetsByIds(list)).thenReturn(tweets);


        Mention m = new Mention();
        m.setUserId(1L);
        Set<UUID> set = new HashSet<>();
        set.add(uid);
        m.setPostIds(set);

        Mockito.when(mentionService.createMention(1L,uid )).thenReturn(Optional.of(m));


        Mockito.when(mentionService.find(1L)).thenReturn(Optional.of(m));

        mockMvc = MockMvcBuilders.standaloneSetup(tweetController).build();

    }


    @Test
    public void testCreateTest() throws Exception {

        Tweet dto = new Tweet();

        dto.setContent("Hello world test");
        dto.setUserId(1L);

        mockMvc.perform(post("/tweet")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(dto))
        )
                .andExpect(status().isCreated());
    }

    @Test(expected = NestedServletException.class)
    public void tesNOTtCreateTest() throws Exception {

        Tweet dto = new Tweet();

        mockMvc.perform(post("/tweet")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(dto))
        );
    }


    @Test
    public void testFindTweet() throws Exception {

        mockMvc.perform(get("/tweet/f0d12829-cb9b-4be9-837d-d75e931ba899")
                .contentType(APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isFound());
    }


    @Test(expected = NestedServletException.class)
    public void testFindTweetNOT() throws Exception {

        mockMvc.perform(get("/tweet/f0d12829-cb9b-4be9-837d-d75e931ba891")
                .contentType(APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isNotFound());
    }


    @Test
    public void testFindTweets() throws Exception {

        TweetList t = new TweetList();

        List<String> list = new ArrayList<>();
        list.add("f0d12829-cb9b-4be9-837d-d75e931ba899");
        list.add("00d12829-cb9b-4be9-837d-d75e931ba899");

        t.setList(list);

        mockMvc.perform(post("/tweets")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(t))

        )
                .andExpect(status().isFound());
    }


    @Test(expected = NestedServletException.class)
    public void testFindTweetsNOT() throws Exception {

        TweetList t = new TweetList();

        List<String> list = new ArrayList<>();
        list.add("f0d12829-cb9b-4be9-837d-d75e931ba891");
        list.add("00d12829-cb9b-4be9-837d-d75e931ba891");

        t.setList(list);

        mockMvc.perform(post("/tweets")
                .contentType(APPLICATION_JSON_UTF8)
                .content(convertObjectToJsonBytes(t))

        )
                .andExpect(status().isFound());
    }

    @Test
    public void testCreateMention() throws Exception {

        mockMvc.perform(post("/tweet/f0d12829-cb9b-4be9-837d-d75e931ba899/mention/1")
                .contentType(APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isCreated());
    }

    @Test(expected = NestedServletException.class)
    public void testCreateMentionFailed() throws Exception {

        mockMvc.perform(post("/tweet/f0d12829-cb9b-4be9-837d-d75e931ba899/mention/2")
                .contentType(APPLICATION_JSON_UTF8)
        )
                ;
    }


    @Test
    public void testFindMention() throws Exception {

        mockMvc.perform(get("/tweet/1/mention")
                .contentType(APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isFound());
    }


    @Test(expected = NestedServletException.class)
    public void testFindMentionFail() throws Exception {

        mockMvc.perform(get("/tweet/2/mention")
                .contentType(APPLICATION_JSON_UTF8)
        )
                .andExpect(status().isFound());
    }

    public  byte[] convertObjectToJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
}
