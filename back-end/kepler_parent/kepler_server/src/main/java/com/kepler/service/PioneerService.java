package com.kepler.service;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kepler.exception.ResourceException;

import com.kepler.model.Pioneer;
import com.kepler.model.UserApplication;

public interface PioneerService {

	/* GENERAL METHODS
	 * *************** */
	Page<Pioneer> getAllPioneers(Pageable pageable);
	
	/* GET METHOD 
	 * *********** */
	Page<Pioneer> getByPioneerId(Long pioneerId, Pageable pageable) throws ResourceException;
	
	Page<Pioneer> getByPioneerLogin(String pioneerLogin, Pageable pageable) throws ResourceException;
	Page<Pioneer> getByPioneerEmail(String pioneerEmail, Pageable pageable) throws ResourceException;
	
	Page<Pioneer> getByPioneerFirstName(String pioneerFirstName, Pageable pageable) throws ResourceException;
	Page<Pioneer> getByPioneerLastName(String pioneerLastName, Pageable pageable) throws ResourceException;
	Page<Pioneer> getByPioneerBirthDate(Date pioneerBirthDate, Pageable pageable) throws ResourceException;
	
	Page<Pioneer> getByPioneerSex(boolean pioneerSex, Pageable pageable) throws ResourceException;
	Page<Pioneer> getByPioneerNotation(Long pioneerNotation, Pageable pageable) throws ResourceException;
	
	Page<Pioneer> getByPioneerUserApplication(UserApplication userApplication, Pageable pageable) throws ResourceException;

	Pioneer saveOrUpdatePioneer(Pioneer pioneer, Long userId, Pageable pageable) throws ResourceException;
	Pioneer deletePioneerId(Long pioneerId, Pageable pageable) throws ResourceException;
}