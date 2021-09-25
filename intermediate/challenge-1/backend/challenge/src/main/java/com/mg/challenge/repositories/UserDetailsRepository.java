package com.mg.challenge.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mg.challenge.User;

@Repository
public interface UserDetailsRepository extends JpaRepository<User, Integer> {

	public User findByUsername(String username);
}
