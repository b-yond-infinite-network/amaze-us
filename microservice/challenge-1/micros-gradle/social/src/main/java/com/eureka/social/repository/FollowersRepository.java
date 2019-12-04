package com.eureka.social.repository;

import com.eureka.social.domain.Followers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowersRepository extends JpaRepository<Followers, String> {

    Optional<Followers> findBySourceIdAndDestinationId(Long sourceId, Long destinationId);

    List<Followers> findByDestinationId(Long destinationId);

}

