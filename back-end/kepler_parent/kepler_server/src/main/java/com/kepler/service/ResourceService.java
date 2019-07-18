package com.kepler.service;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kepler.exception.ResourceException;
import com.kepler.model.Resource;

public interface ResourceService {

	/* GENERAL METHODS
	 * *************** */
	Page<Resource> getAllResources(Pageable pageable);
	
	/* GET METHOD 
	 * *********** */
	Page<Resource> getByResourceId(Long resourceId, Pageable pageable) throws ResourceException;
	Page<Resource> getByResourceName(String resourceName, Pageable pageable) throws ResourceException;
	
	Page<Resource> getByResourceCriticality(Long resourceCriticality, Pageable pageable) throws ResourceException;
	Page<Resource> getByResourceQuantityValue(float resourceQuantityValue, Pageable pageable) throws ResourceException;
	Page<Resource> getByResourceQuantityDate(Date resourceQuantityDate, Pageable pageable) throws ResourceException;

	Page<Resource> getByResourceWarningLevel(float resourceWarningLevel, Pageable pageable) throws ResourceException;
	Page<Resource> getByResourceEmergencyLevel(float resourceEmergencyLevel, Pageable pageable) throws ResourceException;

	Resource saveOrUpdateResource(Resource resource, Pageable pageable) throws ResourceException;
	Resource deleteResourceId(Long resourceId, Pageable pageable) throws ResourceException;
}