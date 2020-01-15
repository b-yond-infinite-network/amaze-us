package com.eureka.tweet.service;

import com.eureka.tweet.domain.Tweet;
import com.eureka.tweet.reposistory.TweetRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.argThat;

@RunWith(MockitoJUnitRunner.class)
public class TweetServiceTests {


    private TweetService tweetService;

    private TweetRepository tweetRepository;
    private RestTemplate restTemplate;


    @Before
    public void setUp() throws Exception {
        tweetRepository = Mockito.mock(TweetRepository.class);
        restTemplate = Mockito.mock(RestTemplate.class);

        tweetService = new TweetService(tweetRepository, restTemplate);

        Tweet tweet = new Tweet();
        tweet.setUserId(1L);
        tweet.setContent("Hello world test");
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Tweet> entity = new HttpEntity<>(tweet, headers);
        UUID uid1 = UUID.fromString("f0d12829-cb9b-4be9-837d-d75e931ba899");
        UUID uid2 = UUID.fromString("f0d12829-cb9b-4be9-837d-d75e931ba898");
        UUID uid3 = UUID.fromString("f0d12829-cb9b-4be9-837d-d75e931ba893");
        UUID uid4 = UUID.fromString("f0d12829-cb9b-4be9-837d-d75e931ba894");

        tweet.setId(uid1);

        Tweet tweet1 = new Tweet();
        tweet.setUserId(1L);
        tweet.setContent("Hello world test1");
        tweet.setId(uid2);

        Mockito
                .when(restTemplate.postForEntity(
                        "http://fanout-service/fanout", entity, Void.class))
                .thenReturn(new ResponseEntity(tweet, HttpStatus.OK));

        Mockito.when(tweetRepository.save(argThat((Tweet aBar) -> aBar.getUserId() == 1L && aBar.getContent().equals("Hello world test")))).thenReturn(tweet);


        Mockito.when(tweetRepository.findByID(uid1)).thenReturn(tweet);

        Mockito.when(tweetRepository.findByID(uid2)).thenReturn(tweet1);

    }


    @Test
    public void testCreateElement() {
        Tweet dto = new Tweet();

        dto.setContent("Hello world test");
        dto.setUserId(1L);

        Optional<Tweet> result = tweetService.createTweet(dto);

        assertTrue(result.isPresent());

        Long id = result.get().getUserId();

        assertEquals(1L, (long) id);
    }

    @Test
    public void testFindElement() {
        Optional<Tweet> found = tweetService.findTweetById("f0d12829-cb9b-4be9-837d-d75e931ba899");

        assertThat(found.isEmpty()).isFalse();

    }


    @Test
    public void testFindElements() {

        List<String> list = new ArrayList<>();
        list.add("f0d12829-cb9b-4be9-837d-d75e931ba899");
        list.add("f0d12829-cb9b-4be9-837d-d75e931ba898");
        List<Tweet> found = tweetService.findTweetsByIds(list);

        assertThat(found.isEmpty()).isFalse();

        assertEquals(2, found.size());

    }

    @Test
    public void testNoSuchFindElements() {

        List<String> list = new ArrayList<>();
        List<Tweet> found = tweetService.findTweetsByIds(list);

        assertThat(found.isEmpty()).isTrue();

        assertEquals(0, found.size());

    }



}
