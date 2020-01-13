package com.eureka.timeline.service;


import com.eureka.common.domain.Range;
import com.eureka.common.domain.dto.TweetDTO;
import com.eureka.common.domain.dto.TweetList;
import com.eureka.common.redis.KeyUtils;
import com.eureka.timeline.config.TestRedisConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestRedisConfiguration.class)
public class RetwisRepositoryTests {

    @Autowired
    private RetwisRepository retwisRepository;
    private RestTemplate restTemplate;

    Map<String, TweetDTO> map;
    List<String> list;
    List<TweetDTO> tweets;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);


        list = new ArrayList<>();
        tweets = new ArrayList<>();
        map = new HashMap<>();
        list.add("4ac72dc3-fab3-496d-b851-44abc3280cd1");
        list.add("4ac72dc3-fab3-496d-b851-44abc3280cd2");
        list.add("4ac72dc3-fab3-496d-b851-44abc3280cd3");
        list.add("4ac72dc3-fab3-496d-b851-44abc3280cd4");
        list.add("4ac72dc3-fab3-496d-b851-44abc3280cd5");
        list.add("4ac72dc3-fab3-496d-b851-44abc3280cd6");
        list.add("4ac72dc3-fab3-496d-b851-44abc3280cd7");
        list.add("4ac72dc3-fab3-496d-b851-44abc3280cd8");
        list.add("4ac72dc3-fab3-496d-b851-44abc3280cd9");
        int i = 0;
       for (String s: list){

           retwisRepository.lSet(KeyUtils.timeline("hola"),s);

           TweetDTO dto = new TweetDTO();
           dto.setId(UUID.fromString(s));
           dto.setContent("Hellow world test "+i);
           dto.setUserId(1L);

           tweets.add(dto);
           map.put(s, dto);
           i++;
       }

        restTemplate = Mockito.mock(RestTemplate.class);


        HttpHeaders header = new HttpHeaders();
        TweetList tweetList = new TweetList();
        tweetList.setList(list);
        HttpEntity<?> entity = new HttpEntity<>(tweetList, header);
        ParameterizedTypeReference<List<TweetDTO>> typeRef = new ParameterizedTypeReference<List<TweetDTO>>(){};


        Mockito
                .when(restTemplate.exchange(
                        "http://tweet-service/tweets",  HttpMethod.POST, entity,typeRef))
                .thenReturn(new ResponseEntity(tweets, HttpStatus.OK));

    }

    @Test

    public void testLGetWorking(){



        List <String> results = retwisRepository.lGet(KeyUtils.timeline("hola"), new Range(0, 2));

        assertEquals(3, results.size());
        assertTrue(!results.isEmpty());
    }


   @Test
   public void testHasMoreTimeline(){
      Range rr =  new Range(0, 2);

       boolean res = retwisRepository.hasMoreTimeline("hola", rr);

       assertTrue(res);
   }

    @Test
    public void testHasMoreTimelineWithoutRange(){
        Range rr =  new Range(0, 2);

        boolean res = retwisRepository.hasMoreTimeline(rr);

        assertFalse(res);
    }


}
