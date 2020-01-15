package com.eureka.auth.repository;

import com.eureka.auth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 *  UserRepository class.
 *
 * @author Miguel Gonzalez (maggonzz@gmail.com)
 * @since 0.0.1
 */
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findOneByUsername(String username);

    Optional<User> findById(Long id);

}

