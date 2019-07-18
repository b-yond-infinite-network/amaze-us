package com.kepler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.kepler.exception.ResourceException;

import com.kepler.model.UserApplication;
import com.kepler.repository.UserApplicationRepository;

@Service(value = "UserApplicationService")
public class UserApplicationServiceImpl implements UserApplicationService {

	@Autowired
	private UserApplicationRepository UserApplicationRepository;

	@Override
	public Page<UserApplication> getAllUserApplications(Pageable pageable) {
		return UserApplicationRepository.findAll(pageable);
	}

	@Override
	public Page<UserApplication> getByUserApplicationId(Long userApplicationId, Pageable pageable) throws ResourceException {
		
		if (!(userApplicationId instanceof Long && userApplicationId >= 0)) {
			throw new ResourceException("[UserApplicationServiceImpl.java] getByUserId", "Can't get the USER with userApplicationId=" + userApplicationId + 
					" because userApplicationId passed isn't a positive integer."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return UserApplicationRepository.findByUserApplicationId(userApplicationId, pageable);
	}

	@Override
	public Page<UserApplication> getByUserApplicationProfile(String userApplicationProfile, Pageable pageable) throws ResourceException {
		
		if (!(userApplicationProfile instanceof String && userApplicationProfile != "")) {
			throw new ResourceException("[UserApplicationServiceImpl.java] getByUserApplicationProfile", "Can't get the USER with userApplicationProfile=" + userApplicationProfile + 
					" because userApplicationProfile passed isn't a not empty String."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return UserApplicationRepository.findByUserApplicationProfile(userApplicationProfile, pageable);
	}

	@Override
	public UserApplication saveOrUpdateUserApplication(UserApplication userApplication, Pageable pageable) throws ResourceException {
		
		Page<UserApplication> result = null;
		
		userApplication.init();
		
		if (userApplication.getUserApplicationProfile() == "") {
			throw new ResourceException("[UserApplicationServiceImpl.java] saveOrUpdateUserApplication", "Can't create or update the userApplication with the userApplicationProfile=" + userApplication.getUserApplicationProfile() + 
					" because userApplicationProfile isn't a none empty String."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		// save the resource
		try {			

			UserApplicationRepository.save(userApplication);
			
		} 
		catch (Exception ex) {
			throw new ResourceException("[UserApplicationServiceImpl.java] saveOrUpdateUserApplication", "Can't create or update the userApplication=" + userApplication + 
					" because the save method raised an error and failed."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		result = getByUserApplicationProfile(userApplication.getUserApplicationProfile(), pageable);
		return result.getContent().get(0);
		
	}

	@Override
	public UserApplication deleteUserApplicationId(Long userApplicationId, Pageable pageable) throws ResourceException {
		
		Page<UserApplication> userApplication_id_found = null;
			
		if (userApplicationId < 0) {
			throw new ResourceException("[UserApplicationServiceImpl.java] deleteUserApplicationId", "Can't delete the userApplication with the userApplicationId=" + userApplicationId + 
					" because deleteUserApplicationId isn't an positive integer."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		/* Check if another element share the same element id */
		userApplication_id_found = UserApplicationRepository.findByUserApplicationId(userApplicationId, pageable);
		if (userApplication_id_found.getNumberOfElements() != 1) {
			throw new ResourceException("[UserApplicationServiceImpl.java] deleteUserApplicationId", "Can't delete the userApplication with the userApplicationId=" + userApplicationId + 
					" because " + userApplication_id_found.getNumberOfElements() + " userApplication(s) found in database."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
				
		if (userApplication_id_found.getContent().get(0).getPioneers().size() > 0) {
			
			throw new ResourceException("[UserApplicationServiceImpl.java] deleteUserApplicationId", "Impossible to delete the userApplication with userApplicationId=" + userApplicationId + 
					"because " + userApplication_id_found.getContent().get(0).getPioneers().size() + " pioneer(s) used it."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		try {			

			UserApplicationRepository.deleteById(userApplicationId);
			
		} 
		catch (Exception ex) {
			throw new ResourceException("[UserApplicationServiceImpl.java] deleteUserApplicationId", "Can't delete the userApplicationId=" + userApplicationId + 
					" because the deleteUserApplicationId method raised an error and failed."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		/* return the first and unique resource of the list */
		return userApplication_id_found.getContent().get(0);
	}

}