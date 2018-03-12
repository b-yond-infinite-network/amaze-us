package com.challenge.userservice.repos;

import org.springframework.data.repository.CrudRepository;

import com.challenge.userservice.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {

}