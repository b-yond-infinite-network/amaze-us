package com.eureka.social.repository;

import com.eureka.social.domain.Following;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowingRepository extends JpaRepository<Following, String> {


    Optional<Following> findBySourceIdAndDestinationId(Long sourceId, Long destinationId);

    List<Following> findBySourceId(Long sourceId);

}

