package com.eureka.social.service;


import com.eureka.social.domain.Following;
import com.eureka.common.domain.dto.FollowDTO;
import com.eureka.social.repository.FollowingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FollowingService {

    private FollowingRepository followingRepository;

    public FollowingService(FollowingRepository followingRepository) {
        this.followingRepository = followingRepository;
    }

    public Following create(FollowDTO followDTO){
        Following newFollower = new Following();
        newFollower.setSourceId(followDTO.getSourceId());
        newFollower.setDestinationId(followDTO.getDestinationId());
        return followingRepository.save(newFollower);
    }

    public Optional<Following> find(FollowDTO followDTO){

        Optional<Following> f =  followingRepository.findBySourceIdAndDestinationId(followDTO.getSourceId(), followDTO.getDestinationId());

        return f;

    }

    public void delete(FollowDTO followDTO){
        Optional<Following>  element  =find(followDTO);
        element.ifPresent(following -> followingRepository.delete(following));
    }

    public List<Following> getFollowing(Long id){
        return followingRepository.findBySourceId(id);
    }

}
