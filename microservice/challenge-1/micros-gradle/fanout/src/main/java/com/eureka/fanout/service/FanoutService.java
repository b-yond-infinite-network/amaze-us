package com.eureka.fanout.service;

import com.eureka.common.domain.dto.FollowDTO;
import com.eureka.common.domain.dto.TweetDTO;
import com.eureka.common.redis.KeyUtils;
import com.eureka.fanout.repository.RedisRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class FanoutService {

    private RestTemplate restTemplate;
    private RedisRepository redisRepository;

    public FanoutService(RestTemplate restTemplate, RedisRepository redisRepository) {
        this.restTemplate = restTemplate;
        this.redisRepository = redisRepository;
    }

    public void fanout(TweetDTO tweetDTO){

        HttpHeaders header = new HttpHeaders();

        System.out.println( "LOL " + tweetDTO.toString());

        HttpEntity<?> entity = new HttpEntity<>(header);
        ParameterizedTypeReference<List<FollowDTO>> typeRef = new ParameterizedTypeReference<List<FollowDTO>>() {};
        ResponseEntity<List<FollowDTO>> response = restTemplate.exchange("http://social-service/social/"+tweetDTO.getUserId()+"/followers", HttpMethod.GET, entity,typeRef);


        if (response.getBody() != null && HttpStatus.OK == response.getStatusCode()){
            List<FollowDTO> followers = response.getBody();

            for (FollowDTO follower : followers){
                System.out.println(follower.toString());
                Long followerId = follower.getSourceId();

                StringBuilder str = new StringBuilder();
                str.append(KeyUtils.UID);
                str.append(followerId);
                str.append(":");
                str.append(KeyUtils.timeline());
                redisRepository.lSet(str.toString(), tweetDTO.getId().toString());
            }
        }

        StringBuilder str = new StringBuilder();
        str.append(KeyUtils.UID);
        str.append(tweetDTO.getUserId());
        str.append(":");
        str.append(KeyUtils.timeline());
        redisRepository.lSet(str.toString(), tweetDTO.getId().toString());
    }
}
