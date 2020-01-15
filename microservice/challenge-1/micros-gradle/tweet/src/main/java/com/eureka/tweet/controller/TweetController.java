package com.eureka.tweet.controller;

import com.eureka.common.domain.dto.TweetList;
import com.eureka.tweet.domain.Mention;
import com.eureka.tweet.domain.Tweet;
import com.eureka.tweet.exception.TweetException;
import com.eureka.tweet.service.MentionService;
import com.eureka.tweet.service.TweetService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;


/**
 * Tweet controller.
 *
 * @author Miguel Gonzalez (maggonzz@gmail.com)
 * @since 0.0.1
 */
@RestController
public class TweetController {

    private TweetService tweetService;
    private MentionService mentionService;

    public TweetController(TweetService tweetService, MentionService mentionService) {
        this.tweetService = tweetService;
        this.mentionService = mentionService;
    }

    @HystrixCommand(fallbackMethod = "fallback")
    @RequestMapping(value = "/tweet", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addTweet(@RequestBody Tweet tweet){

        Optional<Tweet> newTweet = tweetService.createTweet(tweet);

        if (!newTweet.isPresent()) throw new TweetException("Couldn't create tweet");

        return new ResponseEntity<>(newTweet.get(), HttpStatus.CREATED);

    }

    // a fallback method to be called if failure happened
    public Tweet fallback(Throwable hystrixCommand) {
        return new Tweet();
    }

    @GetMapping(value = "/tweet/{id}")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<?> findTweet(@PathVariable String id){
        Optional<Tweet> newTweet = tweetService.findTweetById(id);
        if (!newTweet.isPresent()) throw new TweetException(NOT_FOUND.getReasonPhrase());
        return new ResponseEntity<>(newTweet.get(), HttpStatus.FOUND);
    }

    @PostMapping(value = "/tweets")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<?> getTweets(@RequestBody TweetList list) {
        List<Tweet> result =  tweetService.findTweetsByIds(list.getList());
        if (result.isEmpty()) throw new TweetException(NOT_FOUND.getReasonPhrase());
        return new ResponseEntity<>(result, HttpStatus.FOUND);
    }

    @PostMapping(value = "/tweet/{pid}/mention/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createMention(@PathVariable Long id,@PathVariable UUID pid) {
        Optional<Mention> m = mentionService.createMention(id, pid);
        if (m.isEmpty()) throw new TweetException("Couldn't create mention");
        return new ResponseEntity<>(m, HttpStatus.CREATED);

    }

    @GetMapping(value = "/tweet/{id}/mention")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<?> findMention(@PathVariable Long id){
        Optional<Mention> m = mentionService.find(id);
        if (!m.isPresent()) throw new TweetException(NOT_FOUND.getReasonPhrase());
        return new ResponseEntity<>(m.get(), HttpStatus.FOUND);
    }
}
