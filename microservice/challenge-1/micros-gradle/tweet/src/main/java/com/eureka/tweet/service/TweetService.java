package com.eureka.tweet.service;

import com.eureka.tweet.domain.Tweet;
import com.eureka.tweet.reposistory.TweetRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.datastax.driver.core.utils.UUIDs.timeBased;
import static com.datastax.driver.core.utils.UUIDs.unixTimestamp;

@Service
public class TweetService {

    private TweetRepository tweetRepository;

    private RestTemplate restTemplate;

    public TweetService(TweetRepository tweetRepository, RestTemplate restTemplate) {
        this.tweetRepository = tweetRepository;
        this.restTemplate = restTemplate;
    }

    public Optional<Tweet> createTweet(Tweet newTweet){
        UUID id = timeBased();
        newTweet.setId(id);
        newTweet.setTimestamp(unixTimestamp(id));

        Optional<Tweet> result = Optional.of(tweetRepository.save(newTweet));

        if (result.isPresent()) {
            fanout(result.get());
        }

        return result;

    }

    @Async
    public void fanout(Tweet result){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<Tweet> entity = new HttpEntity<>(result, headers);
        ResponseEntity<Void> response = restTemplate.postForEntity("http://fanout-service/fanout",entity, Void.class );
    }

    public Optional<Tweet> findTweetById(String id){
        UUID uid = UUID.fromString(id);
        return Optional.of(tweetRepository.findByID(uid));
    }

    public List<Tweet> findTweetsByIds(List<String> list){

        List<Tweet> returnList = new ArrayList<>();

        for ( String id : list){

            Optional<Tweet> res =  findTweetById(id);
            res.ifPresent(returnList::add);
        }

        return returnList;
    }

}
