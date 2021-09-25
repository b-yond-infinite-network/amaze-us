package com.mg.challenge.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mg.challenge.Authority;
import com.mg.challenge.User;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

	public Authority findByCode(String code);
}
