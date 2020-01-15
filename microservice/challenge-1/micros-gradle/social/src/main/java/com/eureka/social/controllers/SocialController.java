package com.eureka.social.controllers;

import com.eureka.social.domain.Followers;
import com.eureka.social.domain.Following;
import com.eureka.common.domain.dto.FollowDTO;
import com.eureka.social.service.FollowersService;
import com.eureka.social.service.FollowingService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Social controller.
 *
 * @author Miguel Gonzalez (maggonzz@gmail.com)
 * @since 0.0.1
 */
@RestController
public class SocialController {

    private FollowersService followersService;

    private FollowingService followingService;

    public SocialController(FollowersService followersService, FollowingService followingService) {
        this.followersService = followersService;
        this.followingService = followingService;
    }

    @PostMapping(value = "/social")
    @ResponseStatus(HttpStatus.CREATED)
    public void follow(@RequestBody FollowDTO followDTO){
        followingService.create(followDTO);
        followersService.create(followDTO);
    }

    @DeleteMapping(value = "/social")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unfollow(@RequestBody FollowDTO followDTO){
        followingService.delete(followDTO);
        followersService.delete(followDTO);
    }

    @GetMapping(value = "/social/{userId}/followers")
    @ResponseStatus(HttpStatus.OK)
    public List<Followers> getFollowers(@PathVariable Long userId){
        return followersService.getFollowers(userId);
    }

    @GetMapping(value = "/social/{userId}/following")
    @ResponseStatus(HttpStatus.OK)
    public List<Following> getFollowing(@PathVariable Long userId){
        return followingService.getFollowing(userId);
    }

    @GetMapping(value = "/social/{sourceId}/{destinationId}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isFollowing(@PathVariable Long sourceId, @PathVariable Long destinationId){

        System.out.println("dest "+ destinationId);
        System.out.println("src "+ sourceId);

        FollowDTO f = new FollowDTO();
        f.setDestinationId(destinationId);
        f.setSourceId(sourceId);
        Optional<Following> followDTO =followingService.find(f);

        if(followDTO.isPresent()){
            return true;
        }

        return false;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/social/common/{sourceId}/{destinationId}")
    public Set<Long> commonFollowers(@PathVariable Long sourceId, @PathVariable Long destinationId){

        List<Followers> sourceFollowers = followersService.getFollowers(sourceId);
       List<Followers> destFollowers = followersService.getFollowers(destinationId);

        List<Long> source = sourceFollowers.stream()
                .map(Followers::getSourceId)
                .collect(Collectors.toList());


        List<Long> dest = destFollowers.stream()
                .map(Followers::getSourceId)
                .collect(Collectors.toList());

        Set<Long> set = new HashSet<>(source);

        set.retainAll(dest);
        return set;
    }

}
