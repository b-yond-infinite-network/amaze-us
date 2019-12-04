package com.eureka.auth.repository;

import com.eureka.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findOneByUsername(String username);

    Optional<User> findById(Long id);

}

