package com.kepler.service;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kepler.exception.ResourceException;
import com.kepler.model.ResourceVariation;

public interface ResourceVariationService {

	/* GENERAL METHODS
	 * *************** */
	Page<ResourceVariation> getAllResourceVariations(Pageable pageable);
	
	/* GET METHOD 
	 * *********** */
	Page<ResourceVariation> getByResourceVariationId(Long resourceVariationId, Pageable pageable) throws ResourceException;
	
	Page<ResourceVariation> getByResourceVariationValue(float resourceVariationValue, Pageable pageable) throws ResourceException;
	Page<ResourceVariation> getByResourceVariationDate(Date resourceVariationDate, Pageable pageable) throws ResourceException;
	Page<ResourceVariation> getByResourceVariationApplication(boolean resourceVariationApplication, Pageable pageable) throws ResourceException;
		
	ResourceVariation saveOrUpdateResourceVariation(ResourceVariation resourceVariation, Long resourceId, Pageable pageable) throws ResourceException;
	ResourceVariation deleteResourceVariationId(Long resourceVariationId, Pageable pageable) throws ResourceException;
}