package com.eureka.timeline.controller;

import com.eureka.common.domain.Range;
import com.eureka.common.domain.dto.TweetDTO;
import com.eureka.timeline.service.RetwisRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TimelineController {

    public TimelineController(RetwisRepository retwisRepository) {
        this.retwisRepository = retwisRepository;
    }

    private RetwisRepository retwisRepository;


    @RequestMapping(value = "/timeline/{userId}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<TweetDTO> getTimeline(@PathVariable Long userId, @RequestParam(required = false) Integer page) {
        page = (page != null ? Math.abs(page) : 1);
        Range range = new Range(page);
        return retwisRepository.getTimeline(String.valueOf(userId), range);
    }

    @RequestMapping(value = "/timeline/{userId}/more", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public boolean hasMoreTimeline(@PathVariable String userId, @RequestParam(required = false) Integer page){
        page = (page != null ? Math.abs(page) : 1);
        Range range = new Range(page);
        return retwisRepository.hasMoreTimeline(userId, range);
    }

}
