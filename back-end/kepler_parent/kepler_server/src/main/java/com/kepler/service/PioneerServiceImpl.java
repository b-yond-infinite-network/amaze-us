package com.kepler.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.kepler.exception.ResourceException;
import com.kepler.model.UserApplication;
import com.kepler.repository.UserApplicationRepository;
import com.kepler.model.Pioneer;
import com.kepler.repository.PioneerRepository;

@Service(value = "PioneerService")
public class PioneerServiceImpl implements PioneerService {

	public static final DateFormat date_formatter = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.FRENCH);
	
	public static DateFormat getDateFormatter() {
		return date_formatter;
	}
	
	@Autowired
	private UserApplicationRepository UserApplicationRepository;
	
	@Autowired
	private PioneerRepository PioneerRepository;
	
	
	@Override
	public Page<Pioneer> getAllPioneers(Pageable pageable) {
		return PioneerRepository.findAll(pageable);
	}

	@Override
	public Page<Pioneer> getByPioneerId(Long pioneerId, Pageable pageable)
			throws ResourceException {
		
		if (!(pioneerId instanceof Long && pioneerId >= 0)) {
			throw new ResourceException("[PioneerServiceImpl.java] getByPioneerId", "Can't get the PIONEER with pioneerId=" + pioneerId + 
					" because pioneerId passed isn't a positive integer."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return PioneerRepository.findByPioneerId(pioneerId, pageable);
	}

	@Override
	public Page<Pioneer> getByPioneerLogin(String pioneerLogin, Pageable pageable)
			throws ResourceException {
		
		if (!(pioneerLogin instanceof String && pioneerLogin != "")) {
			throw new ResourceException("[PioneerServiceImpl.java] getByPioneerLogin", "Can't get all the PIONEER with pioneerLogin=" + pioneerLogin + 
					" because pioneerLogin passed isn't a none empty String."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return PioneerRepository.findByPioneerLogin(pioneerLogin, pageable);
		
	}
	
	@Override
	public Page<Pioneer> getByPioneerEmail(String pioneerEmail, Pageable pageable) throws ResourceException {
		
		if (!(pioneerEmail instanceof String && pioneerEmail != "")) {
			throw new ResourceException("[PioneerServiceImpl.java] getByPioneerEmail", "Can't get all the PIONEER with pioneerEmail=" + pioneerEmail + 
					" because pioneerEmail passed isn't a none empty String."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return PioneerRepository.findByPioneerEmail(pioneerEmail, pageable);
	}
	
	@Override
	public Page<Pioneer> getByPioneerFirstName(String pioneerFirstName, Pageable pageable)
			throws ResourceException {
		if (!(pioneerFirstName instanceof String && pioneerFirstName != "")) {
			throw new ResourceException("[PioneerServiceImpl.java] getByPioneerFirstName", "Can't get all the PIONEER with pioneerFirstName=" + pioneerFirstName + 
					" because pioneerFirstName passed isn't a none empty String."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return PioneerRepository.findByPioneerFirstName(pioneerFirstName, pageable);
		
	}
	
	@Override
	public Page<Pioneer> getByPioneerLastName(String pioneerLastName, Pageable pageable)
			throws ResourceException {
		if (!(pioneerLastName instanceof String && pioneerLastName != "")) {
			throw new ResourceException("[PioneerServiceImpl.java] getByPioneerLastName", "Can't get all the PIONEER with pioneerLastName=" + pioneerLastName + 
					" because pioneerLastName passed isn't a none empty String."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return PioneerRepository.findByPioneerLastName(pioneerLastName, pageable);
		
	}

	@Override
	public Page<Pioneer> getByPioneerBirthDate(Date pioneerBirthDate, Pageable pageable)
			throws ResourceException {
		
		if (!(pioneerBirthDate instanceof Date)) {
			throw new ResourceException("[PioneerServiceImpl.java] getByPioneerBirthDate", "Can't get all the PIONEER with pioneerBirthDate=" + pioneerBirthDate + 
					" because pioneerBirthDate passed isn't a Date."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return PioneerRepository.findByPioneerBirthDate(pioneerBirthDate, pageable);
	}

	@Override
	public Page<Pioneer> getByPioneerSex(boolean pioneerSex, Pageable pageable) throws ResourceException {
				
		return PioneerRepository.findByPioneerSex(pioneerSex, pageable);
	}
	
	@Override
	public Page<Pioneer> getByPioneerNotation(Long pioneerNotation, Pageable pageable)
			throws ResourceException {
		
		if (!(pioneerNotation instanceof Long && pioneerNotation >= 0)) {
			throw new ResourceException("[PioneerServiceImpl.java] getByPioneerNotation", "Can't get the PIONEER with pioneerNotation=" + pioneerNotation + 
					" because pioneerNotation passed isn't a positive integer."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return PioneerRepository.findByPioneerNotation(pioneerNotation, pageable);
	}
	
	@Override
	public Page<Pioneer> getByPioneerUserApplication(UserApplication userApplication, Pageable pageable) throws ResourceException {
		
		Page<UserApplication> result = null;
		
		Long userApplicationId = userApplication.getUserApplicationId();
		String userApplicationProfile = userApplication.getUserApplicationProfile();
		
		if (userApplicationId != null && userApplicationId >= 0) {
			result = UserApplicationRepository.findByUserApplicationId(userApplicationId, pageable);
		} else if (userApplicationProfile != null && userApplicationProfile != "") {
			result = UserApplicationRepository.findByUserApplicationId(userApplicationId, pageable);
		} else {
			throw new ResourceException("[PioneerServiceImpl.java] getByPioneerUserApplication", "Can't get the pioneers sharing the userApplication=" + userApplication + 
					" because userApplication is empty on its ID and Profile fields."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (result.getTotalElements() != 1) {
			throw new ResourceException("[PioneerServiceImpl.java] getByPioneerUserApplication", "Can't get the pioneers sharing the userApplication=" + userApplication + 
					" because " + result.getNumberOfElements() + " userApplication(s) found in database."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return PioneerRepository.findByUserApplication(result.getContent().get(0), pageable);

	}

	@Override
	public Pioneer saveOrUpdatePioneer(Pioneer pioneer, Long userApplicationId, Pageable pageable)
			throws ResourceException {
		
		Date now = null;
		Page<Pioneer> result = null;
		Page<UserApplication> userApplication_searched = null;
		
		pioneer.init();
		now = new Date();
		
		if (pioneer.getPioneerLogin() == "" || pioneer.getPioneerLogin() == null) {
			throw new ResourceException("[PioneerServiceImpl.java] saveOrUpdatePioneer", "Can't create or update the pioneer with the pioneerLogin=" + pioneer.getPioneerLogin() + 
					" because pioneerLogin is empty or null."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		} else if (pioneer.getPioneerPassword() == "" || pioneer.getPioneerPassword() == null) {
			throw new ResourceException("[PioneerServiceImpl.java] saveOrUpdatePioneer", "Can't create or update the pioneer with the pioneerPassword=" + pioneer.getPioneerPassword() + 
					" because pioneerPassword is empty or null."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		} else if (pioneer.getPioneerEmail() == "" || pioneer.getPioneerEmail() == null) {
			throw new ResourceException("[PioneerServiceImpl.java] saveOrUpdatePioneer", "Can't create or update the pioneer with the pioneerEmail=" + pioneer.getPioneerEmail() + 
					" because pioneerEmail is empty or null."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		} else if (pioneer.getPioneerFirstName() == "" || pioneer.getPioneerFirstName() == null) {
			throw new ResourceException("[PioneerServiceImpl.java] saveOrUpdatePioneer", "Can't create or update the pioneer with the pioneerFirstName=" + pioneer.getPioneerFirstName() + 
					" because pioneerFirstName is empty or null."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		} else if (pioneer.getPioneerLastName() == "" || pioneer.getPioneerLastName() == null) {
			throw new ResourceException("[PioneerServiceImpl.java] saveOrUpdatePioneer", "Can't create or update the pioneer with the pioneerLastName=" + pioneer.getPioneerLastName() + 
					" because pioneerLastName is empty or null."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		} else if (pioneer.getPioneerBirthDate() != null && pioneer.getPioneerBirthDate().after(now)) {
			throw new ResourceException("[PioneerServiceImpl.java] saveOrUpdatePioneer", "Can't create or update the pioneer with the pioneerBirthDate=" + pioneer.getPioneerBirthDate() + 
					" because pioneerBirthDate is after now (" + now + ")"
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}  else if (pioneer.getPioneerNotation() < 0) {
			throw new ResourceException("[PioneerServiceImpl.java] saveOrUpdatePioneer", "Can't create or update the pioneer with the pioneerNotation=" + pioneer.getPioneerNotation() + 
					" because pioneerNotation is after now (" + now + ")"
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}  
		
		if (pioneer.getUserApplication() != null && pioneer.getUserApplication().getUserApplicationId() != null) {
			
			userApplication_searched = UserApplicationRepository.findByUserApplicationId(pioneer.getUserApplication().getUserApplicationId(), pageable);
			
			if (userApplication_searched.getNumberOfElements() != 1) {
				throw new ResourceException("[PioneerServiceImpl.java] saveOrUpdatePioneer", "Can't create or update the pioneer with the userApplication=" + pioneer.getUserApplication() + 
						" because userApplication passed doesn't refer to a real userApplication saved in the database."
						, HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				
				pioneer.setUserApplication(userApplication_searched.getContent().get(0));
				
			}
			
		} else {
			
			throw new ResourceException("[PioneerServiceImpl.java] saveOrUpdatePioneer", "Can't create or update the pioneer pioneer=" + pioneer + 
					" because no valid userApplicationId found and associate to the pioneer."
					, HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
		result = getByPioneerLogin(pioneer.getPioneerLogin(), pageable);
		if (result.getNumberOfElements() == 1) {
			pioneer.setPioneerId(result.getContent().get(0).getPioneerId());
		} else {
			result = getByPioneerEmail(pioneer.getPioneerEmail(), pageable);
			if (result.getNumberOfElements() == 1) {
				pioneer.setPioneerId(result.getContent().get(0).getPioneerId());
			}
		}
		
		// save the resource
		try {			

			PioneerRepository.save(pioneer);
			
		} 
		catch (Exception ex) {
			throw new ResourceException("[PioneerServiceImpl.java] saveOrUpdatePioneer", "Can't create or update the pioneer=" + pioneer + 
					" because the save method raised an error and failed."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		result = getByPioneerLogin(pioneer.getPioneerLogin(), pageable);
		if (result.getNumberOfElements() == 1) {
			return result.getContent().get(0);
		}
		
		result = getByPioneerEmail(pioneer.getPioneerEmail(), pageable);
		return result.getContent().get(0);	
	}

	@Override
	public Pioneer deletePioneerId(Long pioneerId, Pageable pageable) throws ResourceException {
		
		Page<Pioneer> pioneer_found = null;

		if (pioneerId < 0) {
			throw new ResourceException("[PioneerServiceImpl.java] deletePioneerId", "Can't delete the pioneer with the pioneerId=" + pioneerId + 
					" because pioneerId isn't an positive integer."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		/* Check if another element share the same element id */
		pioneer_found = PioneerRepository.findByPioneerId(pioneerId, pageable);
		if (pioneer_found.getNumberOfElements() != 1) {
			throw new ResourceException("[PioneerServiceImpl.java] deletePioneerId", "Can't delete the pioneer with the pioneerId=" + pioneerId + 
					" because " + pioneer_found.getNumberOfElements() + " pioneer(s) found in database."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		try {			

			PioneerRepository.deleteById(pioneerId);
			
		} 
		catch (Exception ex) {
			throw new ResourceException("[PioneerServiceImpl.java] deletePioneerId", "Can't delete the pioneerId=" + pioneerId + 
					" because the deleteById method raised an error and failed."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		/* return the first and unique resource of the list */
		return pioneer_found.getContent().get(0);
	}

	
	
}