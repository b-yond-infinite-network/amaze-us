package com.kepler.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kepler.model.UserApplication;

@Repository
public interface UserApplicationRepository extends JpaRepository<UserApplication, Long> {
	
	/* ********************************************************************************************************* *
	 * CRUD requests are missing but are implemented due to the extension with JpaRepostroy<ENtity, primary_key> * 
	 * CRUD : Create / Read / Update and Delete requests                                                         * 
	 * ********************************************************************************************************* *
	 * If a function requires to apply a complex request, the association @Query(the_request) can be used before */
	
	/* Next queries are added due to custom needs */
	Page<UserApplication> findByUserApplicationId(Long userApplicationId, Pageable pageable);
	Page<UserApplication> findByUserApplicationProfile(String userApplicationProfile, Pageable pageable);
}
