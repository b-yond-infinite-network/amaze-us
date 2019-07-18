package com.kepler.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.kepler.exception.ResourceException;
import com.kepler.model.Resource;
import com.kepler.repository.ResourceRepository;
import com.kepler.model.ResourceVariation;
import com.kepler.repository.ResourceVariationRepository;

@Service(value = "ResourceVariationService")
public class ResourceVariationServiceImpl implements ResourceVariationService {

	public static final DateFormat date_formatter = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.FRENCH);
	
	public static DateFormat getDateFormatter() {
		return date_formatter;
	}

	@Autowired
	private ResourceRepository ResourceRepository;
	
	@Autowired
	private ResourceVariationRepository ResourceVariationRepository;
	
	
	@Override
	public Page<ResourceVariation> getAllResourceVariations(Pageable pageable) {
		return ResourceVariationRepository.findAll(pageable);
	}

	@Override
	public Page<ResourceVariation> getByResourceVariationId(Long resourceVariationId, Pageable pageable)
			throws ResourceException {
		
		if (!(resourceVariationId instanceof Long && resourceVariationId >= 0)) {
			throw new ResourceException("[ResourceVariationServiceImpl.java] getByResourceVariationId", "Can't get the RESOURCE_VARIATION with resourceVariationId=" + resourceVariationId + 
					" because resourceVariationId passed isn't a positive integer."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return ResourceVariationRepository.findByResourceVariationId(resourceVariationId, pageable);
	}

	@Override
	public Page<ResourceVariation> getByResourceVariationValue(float resourceVariationValue, Pageable pageable)
			throws ResourceException {
		if (resourceVariationValue == 0.0) {
			throw new ResourceException("[ResourceVariationServiceImpl.java] getByResourceVariationValue", "Can't get all the RESOURCE_VARIATION with resourceVariationValue=" + resourceVariationValue + 
					" because resourceVariationValue passed isn't a not zero float."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return ResourceVariationRepository.findByResourceVariationValue(resourceVariationValue, pageable);
		
	}

	@Override
	public Page<ResourceVariation> getByResourceVariationDate(Date resourceVariationDate, Pageable pageable)
			throws ResourceException {
		
		if (!(resourceVariationDate instanceof Date)) {
			throw new ResourceException("[ResourceVariationServiceImpl.java] getByResourceVariationDate", "Can't get all the RESOURCE_VARIATION with resourceVariationDate=" + resourceVariationDate + 
					" because resourceVariationDate passed isn't a Date."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return ResourceVariationRepository.findByResourceVariationDate(resourceVariationDate, pageable);
	}

	@Override
	public Page<ResourceVariation> getByResourceVariationApplication(boolean resourceVariationApplication, Pageable pageable) throws ResourceException {
				
		return ResourceVariationRepository.findByResourceVariationApplication(resourceVariationApplication, pageable);
	}
	
	@Override
	public ResourceVariation saveOrUpdateResourceVariation(ResourceVariation resourceVariation, Long resourceId, Pageable pageable)
			throws ResourceException {
		
		Date now = null;
		Page<Resource> resource_searched = null;
		
		resourceVariation.init();
		
		if (resourceVariation.getResourceVariationValue() == 0.0) {
			throw new ResourceException("[ResourceVariationServiceImpl.java] saveOrUpdateResourceVariation", "Can't create or update the resource_variation with the resourceVariationValue=" + resourceVariation.getResourceVariationValue() + 
					" because resourceVariationValue is null."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		// if Date is null -> get the current date
		if (resourceVariation.getResourceVariationDate() == null) {
			
		    now = new Date();
		    
		    try {
		    	
				now = getDateFormatter().parse(getDateFormatter().format(now));
				
			} catch (ParseException e) {
				
				throw new ResourceException("[ResourceVariationServiceImpl.java] saveOrUpdateResourceVariation", "resourceVariationDate set to null and impossible to set it with the current date because parsing raised an error and failed."
						, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		    
		    resourceVariation.setResourceVariationDate(now);
		}
		
		resource_searched = ResourceRepository.findByResourceId(resourceId, pageable);
		if (resource_searched.getNumberOfElements() != 1) {
			throw new ResourceException("[ResourceVariationServiceImpl.java] saveOrUpdateResourceVariation", "Can't create or update the resource_variation with the resourceId=" + resourceId + 
					" because resourceId passed doesn't refer to a real resource saved in the database."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			resourceVariation.setResource(resource_searched.getContent().get(0));
			
		}
		
		// save the resource
		try {			

			return ResourceVariationRepository.save(resourceVariation);
			
		} 
		catch (Exception ex) {
			throw new ResourceException("[ResourceVariationServiceImpl.java] saveOrUpdateResourceVariation", "Can't create or update the resource_variation=" + resourceVariation + 
					" because the save method raised an error and failed."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ResourceVariation deleteResourceVariationId(Long resourceVariationId, Pageable pageable) throws ResourceException {
		
		Page<ResourceVariation> resourceVariation_id_found = null;

		if (resourceVariationId < 0) {
			throw new ResourceException("[ResourceVariationServiceImpl.java] deleteResourceVariationId", "Can't delete the resourceVariation with the resourceVariationId=" + resourceVariationId + 
					" because resourceVariationId isn't an positive integer."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		/* Check if another element share the same element id */
		resourceVariation_id_found = ResourceVariationRepository.findByResourceVariationId(resourceVariationId, pageable);
		if (resourceVariation_id_found.getNumberOfElements() != 1) {
			throw new ResourceException("[ResourceVariationServiceImpl.java] deleteResourceVariationId", "Can't delete the resourceVariation with the resourceVariationId=" + resourceVariationId + 
					" because " + resourceVariation_id_found.getContent().size() + " resourceVariation(s) found in database."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		try {			

			ResourceVariationRepository.deleteById(resourceVariationId);
			
		} 
		catch (Exception ex) {
			throw new ResourceException("[ResourceVariationServiceImpl.java] deleteResourceVariationId", "Can't delete the resourceVariationId=" + resourceVariationId + 
					" because the deleteById method raised an error and failed."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		/* return the first and unique resource of the list */
		return resourceVariation_id_found.getContent().get(0);
	}
	
}