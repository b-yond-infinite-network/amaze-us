package com.eureka.fanout.controller;

import com.eureka.common.domain.dto.TweetDTO;
import com.eureka.fanout.service.FanoutService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Fanout controller
 *
 * @author Miguel Gonzalez (maggonzz@gmail.com)
 * @since 0.0.1
 */
@RestController
public class FanoutController {

    private FanoutService fanoutService;

    public FanoutController(FanoutService fanoutService) {
        this.fanoutService = fanoutService;
    }

    @PostMapping(value = "/fanout")
    @ResponseStatus(HttpStatus.OK)
    public void tweetFanout(@RequestBody TweetDTO tweetDTO){
        fanoutService.fanout(tweetDTO);
    }
}
