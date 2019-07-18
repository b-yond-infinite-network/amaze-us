package com.kepler.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kepler.model.UserApplication;
import com.kepler.model.Pioneer;

@Repository
public interface PioneerRepository extends JpaRepository<Pioneer, Long> {
	
	/* ********************************************************************************************************* *
	 * CRUD requests are missing but are implemented due to the extension with JpaRepostroy<ENtity, primary_key> * 
	 * CRUD : Create / Read / Update and Delete requests                                                         * 
	 * ********************************************************************************************************* *
	 * If a function requires to apply a complex request, the association @Query(the_request) can be used before */
	
	/* Next queries are added due to custom needs */
	Page<Pioneer> findByPioneerId(Long pioneerId, Pageable pageable);
	
	Page<Pioneer> findByPioneerLogin(String pioneerLogin, Pageable pageable);
	Page<Pioneer> findByPioneerEmail(String pioneerEmail, Pageable pageable);
	
	Page<Pioneer> findByPioneerFirstName(String pioneerFirstName, Pageable pageable);
	Page<Pioneer> findByPioneerLastName(String pioneerLastName, Pageable pageable);
	Page<Pioneer> findByPioneerBirthDate(Date pioneerBirthDate, Pageable pageable);
	
	Page<Pioneer> findByPioneerSex(boolean pioneerSex, Pageable pageable);
	Page<Pioneer> findByPioneerNotation(Long pioneerNotation, Pageable pageable);

	Page<Pioneer> findByUserApplication(UserApplication userApplication, Pageable pageable);
}
