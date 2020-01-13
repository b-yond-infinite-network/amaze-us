package com.eureka.fanout.service;


import com.eureka.common.domain.dto.FollowDTO;
import com.eureka.common.domain.dto.TweetDTO;
import com.eureka.fanout.repository.RedisRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class FanoutServiceTests {

    private FanoutService fanoutService;
    private RedisRepository redisRepository;
    private RestTemplate restTemplate;

    @Before
    public void setUp() throws Exception {
        redisRepository = Mockito.mock(RedisRepository.class);
        restTemplate = Mockito.mock(RestTemplate.class);

        fanoutService = new FanoutService(restTemplate,redisRepository);
        HttpHeaders header = new HttpHeaders();

        HttpEntity<?> entity = new HttpEntity<>(header);
        ParameterizedTypeReference<List<FollowDTO>> typeRef = new ParameterizedTypeReference<List<FollowDTO>>() {};

        List<FollowDTO> followers = new ArrayList<>();

        FollowDTO f1 = new FollowDTO();
        f1.setSourceId(2L);
        f1.setDestinationId(1L);
        FollowDTO f2 = new FollowDTO();
        f2.setSourceId(2L);
        f2.setDestinationId(1L);


        followers.add(f1);
        followers.add(f2);

        TweetDTO tweetDTO = new TweetDTO();
        tweetDTO.setContent("Hello world test");
        UUID uid2 = UUID.fromString("f0d12829-cb9b-4be9-837d-d75e931ba898");
        tweetDTO.setUserId(1L);
        tweetDTO.setContent("Hello world test1");
        tweetDTO.setId(uid2);

        Mockito
                .when(restTemplate.exchange(
                        "http://social-service/social/"+tweetDTO.getUserId()+"/followers",  HttpMethod.GET, entity,typeRef))
                .thenReturn(new ResponseEntity(followers, HttpStatus.OK));
        Mockito.when(redisRepository.lSet(anyString(), anyString())).thenReturn(true);

    }


    @Test
    public void testFanout(){

        TweetDTO tweetDTO = new TweetDTO();
        tweetDTO.setContent("Hello world test");
        UUID uid2 = UUID.fromString("f0d12829-cb9b-4be9-837d-d75e931ba898");
        tweetDTO.setUserId(1L);
        tweetDTO.setContent("Hello world test1");
        tweetDTO.setId(uid2);
        fanoutService.fanout(tweetDTO);
    }


}
