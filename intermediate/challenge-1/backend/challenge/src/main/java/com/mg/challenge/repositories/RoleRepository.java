package com.mg.challenge.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mg.challenge.pojos.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

	public Role findByCode(String code);

}
