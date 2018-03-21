package com.amertkara.udm.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.amertkara.udm.model.entity.User;

@Transactional
public interface UserRepository extends CrudRepository<User, Long> {

	boolean existsByEmail(String email);

	User readByAccountIdentifier(String accountIdentifier);
}
