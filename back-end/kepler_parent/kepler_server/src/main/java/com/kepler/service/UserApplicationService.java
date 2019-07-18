package com.kepler.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kepler.exception.ResourceException;
import com.kepler.model.UserApplication;

public interface UserApplicationService {

	/* GENERAL METHODS
	 * *************** */
	Page<UserApplication> getAllUserApplications(Pageable pageable);
	
	/* GET METHOD 
	 * *********** */
	Page<UserApplication> getByUserApplicationId(Long userApplicationId, Pageable pageable) throws ResourceException;
	Page<UserApplication> getByUserApplicationProfile(String userApplicationProfile, Pageable pageable) throws ResourceException;

	UserApplication saveOrUpdateUserApplication(UserApplication userApplication, Pageable pageable) throws ResourceException;
	UserApplication deleteUserApplicationId(Long userApplicationId, Pageable pageable) throws ResourceException;
}