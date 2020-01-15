package com.eureka.social.service;

import com.eureka.social.domain.Followers;
import com.eureka.common.domain.dto.FollowDTO;
import com.eureka.social.repository.FollowersRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


/**
 * Followers service.
 *
 * @author Miguel Gonzalez (maggonzz@gmail.com)
 * @since 0.0.1
 */
@Service
public class FollowersService {

    private FollowersRepository followersRepository;

    public FollowersService(FollowersRepository followersRepository) {
        this.followersRepository = followersRepository;
    }

    public Followers create(FollowDTO followDTO){
        Followers newFollower = new Followers();
        newFollower.setSourceId(followDTO.getSourceId());
        newFollower.setDestinationId(followDTO.getDestinationId());
        return followersRepository.save(newFollower);
    }

    public Optional<Followers> find(FollowDTO followDTO){
        return followersRepository.findBySourceIdAndDestinationId(followDTO.getSourceId(), followDTO.getDestinationId());
    }

    public void delete(FollowDTO followDTO){
        Optional<Followers>  element  =find(followDTO);
        element.ifPresent(following -> followersRepository.delete(following));
    }

    public List<Followers> getFollowers(Long id){
        return followersRepository.findByDestinationId(id);
    }


}
