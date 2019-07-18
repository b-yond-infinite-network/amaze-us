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

@Service(value = "ResourceService")
public class ResourceServiceImpl implements ResourceService {

	public static final DateFormat date_formatter = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.FRENCH);
	
	public static DateFormat getDateFormatter() {
		return date_formatter;
	}

	@Autowired
	private ResourceRepository ResourceRepository;

	@Override
	public Page<Resource> getAllResources(Pageable pageable) {
		return ResourceRepository.findAll(pageable);
	}

	@Override
	public Page<Resource> getByResourceId(Long resourceId, Pageable pageable) throws ResourceException {
		
		if (!(resourceId instanceof Long && resourceId >= 0)) {
			throw new ResourceException("[ResourceServiceImpl.java] getByResourceId", "Can't get the RESOURCE with resourceId=" + resourceId + 
					" because resourceId passed isn't a positive integer."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return ResourceRepository.findByResourceId(resourceId, pageable);
	}

	@Override
	public Page<Resource> getByResourceName(String resourceName, Pageable pageable) throws ResourceException {
		
		if (!(resourceName instanceof String && resourceName != "")) {
			throw new ResourceException("[ResourceServiceImpl.java] getByResourceName", "Can't get the RESOURCE with resourceName=" + resourceName + 
					" because resourceName passed isn't a not empty String."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return ResourceRepository.findByResourceName(resourceName, pageable);
	}

	@Override
	public Page<Resource> getByResourceCriticality(Long resourceCriticality, Pageable pageable)
			throws ResourceException {

		if (!(resourceCriticality instanceof Long && resourceCriticality >= 0)) {
			throw new ResourceException("[ResourceServiceImpl.java] getByResourceCriticality", "Can't get all the RESOURCE with resourceCriticality=" + resourceCriticality + 
					" because resourceCriticality passed isn't a not positive integer."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return ResourceRepository.findByResourceCriticality(resourceCriticality, pageable);
	}

	@Override
	public Page<Resource> getByResourceQuantityValue(float resourceQuantityValue, Pageable pageable)
			throws ResourceException {
		
		if (resourceQuantityValue < 0.0) {
			throw new ResourceException("[ResourceServiceImpl.java] getByResourceQuantityValue", "Can't get all the RESOURCE with resourceQuantityValue=" + resourceQuantityValue + 
					" because resourceQuantityValue passed isn't a not positive float."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return ResourceRepository.findByResourceQuantityValue(resourceQuantityValue, pageable);
	}

	@Override
	public Page<Resource> getByResourceQuantityDate(Date resourceQuantityDate, Pageable pageable)
			throws ResourceException {
		
		if (!(resourceQuantityDate instanceof Date)) {
			throw new ResourceException("[ResourceServiceImpl.java] getByResourceQuantityDate", "Can't get all the RESOURCE with resourceQuantityDate=" + resourceQuantityDate + 
					" because resourceCriticality passed isn't a Date."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return ResourceRepository.findByResourceQuantityDate(resourceQuantityDate, pageable);
	}

	@Override
	public Page<Resource> getByResourceWarningLevel(float resourceWarningLevel, Pageable pageable)
			throws ResourceException {
		
		if (resourceWarningLevel < 0.0) {
			throw new ResourceException("[ResourceServiceImpl.java] getByResourceWarningLevel", "Can't get all the RESOURCE with resourceWarningLevel=" + resourceWarningLevel + 
					" because resourceWarningLevel passed isn't a not positive float."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return ResourceRepository.findByResourceWarningLevel(resourceWarningLevel, pageable);
	}

	@Override
	public Page<Resource> getByResourceEmergencyLevel(float resourceEmergencyLevel, Pageable pageable)
			throws ResourceException {
		
		if (resourceEmergencyLevel < 0.0) {
			throw new ResourceException("[ResourceServiceImpl.java] getByResourceEmergencyLevel", "Can't get all the RESOURCE with resourceEmergencyLevel=" + resourceEmergencyLevel + 
					" because resourceEmergencyLevel passed isn't a not positive float."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return ResourceRepository.findByResourceEmergencyLevel(resourceEmergencyLevel, pageable);
	}

	@Override
	public Resource saveOrUpdateResource(Resource resource, Pageable pageable) throws ResourceException {
		
		Date now = null;
		Page<Resource> result = null;
		
		resource.init();
		
		if (resource.getResourceName() == "") {
			throw new ResourceException("[ResourceServiceImpl.java] saveOrUpdateResource", "Can't create or update the resource with the resourceName=" + resource.getResourceName() + 
					" because resourceName isn't a none empty String."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		} else if (resource.getResourceCriticality() < 0) {
			throw new ResourceException("[ResourceServiceImpl.java] saveOrUpdateResource", "Can't create or update the resource with the resourceCriticality=" + resource.getResourceCriticality() + 
					" because resourceCriticality isn't an positive integer."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		} else if (resource.getResourceQuantityValue() < 0.0) {
			throw new ResourceException("[ResourceServiceImpl.java] saveOrUpdateResource", "Can't create or update the resource with the resourceQuantityValue=" + resource.getResourceQuantityValue() + 
					" because resourceQuantityValue isn't an positive float."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		} else if (resource.getResourceWarningLevel() < 0.0) {
			throw new ResourceException("[ResourceServiceImpl.java] saveOrUpdateResource", "Can't create or update the resource with the resourceWarningLevel=" + resource.getResourceWarningLevel() + 
					" because resourceWarningLevel isn't an positive float."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		} else if (resource.getResourceEmergencyLevel() < 0.0) {
			throw new ResourceException("[ResourceServiceImpl.java] saveOrUpdateResource", "Can't create or update the resource with the resourceEmergencyLevel=" + resource.getResourceEmergencyLevel() + 
					" because resourceEmergencyLevel isn't an positive float."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		// if Date is null -> get the current date
		if (resource.getResourceQuantityDate() == null) {
			
		    now = new Date();
		    
		    try {
		    	
				now = getDateFormatter().parse(getDateFormatter().format(now));
				
			} catch (ParseException e) {
				
				throw new ResourceException("[ResourceServiceImpl.java] saveOrUpdateResource", "resourceQuantityDate set to null and impossible to set it with the current date because parsing raised an error and failed."
						, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		    
		    resource.setResourceQuantityDate(now);
		}

		// save the resource
		try {			

			ResourceRepository.save(resource);
			
		} 
		catch (Exception ex) {
			throw new ResourceException("[ResourceServiceImpl.java] saveOrUpdateResource", "Can't create or update the resource=" + resource + 
					" because the save method raised an error and failed."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		result = getByResourceName(resource.getResourceName(), pageable);
		return result.getContent().get(0);
		
	}

	@Override
	public Resource deleteResourceId(Long resourceId, Pageable pageable) throws ResourceException {
		
		Page<Resource> resource_id_found = null;

		if (resourceId < 0) {
			throw new ResourceException("[ResourceServiceImpl.java] deleteResource", "Can't delete the resource with the resourceId=" + resourceId + 
					" because resourceId isn't an positive integer."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		/* Check if another element share the same element id */
		resource_id_found = ResourceRepository.findByResourceId(resourceId, pageable);
		if (resource_id_found.getNumberOfElements() != 1) {
			throw new ResourceException("[ResourceServiceImpl.java] deleteResource", "Can't delete the resource with the resourceId=" + resourceId + 
					" because " + resource_id_found.getContent().size() + " resource(s) found in database."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		try {			

			ResourceRepository.deleteById(resourceId);
			
		} 
		catch (Exception ex) {
			throw new ResourceException("[ResourceServiceImpl.java] deleteResource", "Can't delete the resourceId=" + resourceId + 
					" because the deleteById method raised an error and failed."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		/* return the first and unique resource of the list */
		return resource_id_found.getContent().get(0);
	}

}