package com.challenge.userservice.repos;

import org.springframework.data.repository.CrudRepository;

import com.challenge.userservice.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

}