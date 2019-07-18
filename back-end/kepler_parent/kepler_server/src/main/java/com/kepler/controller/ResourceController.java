package com.kepler.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kepler.exception.ResourceException;

import com.kepler.model.Resource;
import com.kepler.service.ResourceService;

import com.kepler.model.Pioneer;
import com.kepler.service.PioneerService;

import com.kepler.model.UserApplication;

@RestController
@RequestMapping("/resource/*")
public class ResourceController {
	
	private static final List<String> profilesAllowed = new ArrayList<String>(Arrays.asList("classic", "checker", "administrator"));
	
	@Autowired
    private ResourceService ResourceService;
	
	@Autowired
    private PioneerService PioneerService;
    
	@Transactional
	private boolean checkAuthorisation(Long pioneerId, Pageable pageable) {
		
		Page<Pioneer> pioneerDB = null;
    	UserApplication userApplicationDB = null;
    	boolean allowed = false; 
		
    	pioneerDB = PioneerService.getByPioneerId(pioneerId, pageable);
    	if (pioneerDB.getTotalElements() != 1) {
    		throw new ResourceException("[ResourceController.java] checkAuthorisation", "Impossible to get the pioneer with pioneerId=" + pioneerId + 
    				" from the database. The access to the REST API are refused."
					, HttpStatus.INTERNAL_SERVER_ERROR);
    	} else {
    		userApplicationDB = pioneerDB.getContent().get(0).getUserApplication();
    	}
    	
    	for (String profileAllowed : ResourceController.profilesAllowed) {
    	   if (userApplicationDB.getUserApplicationProfile().equals(profileAllowed)) {
    		   allowed = true;
    	   }
    	}
    	if (!allowed) {
    		throw new ResourceException("[ResourceController.java] checkAuthorisation", "The UserApplication profile is refused."
					, HttpStatus.INTERNAL_SERVER_ERROR);
    	} else {
    		return true;
    	}
	}	
	
    @GetMapping("/all/{pioneerId}")
	public Page<Resource> getAllResourcesCtrl(@PathVariable("pioneerId") Long pioneerId, Pageable pageable) {

    	checkAuthorisation(pioneerId, pageable);    	
        return ResourceService.getAllResources(pageable);
 	}    
	
    @GetMapping("/id/{resourceId}/{pioneerId}")
    public Page<Resource> getByResourceIdCtrl(@PathVariable("pioneerId") Long pioneerId, @PathVariable("resourceId") Long resourceId, Pageable pageable) {
        
    	checkAuthorisation(pioneerId, pageable);    	
    	return ResourceService.getByResourceId(resourceId, pageable);
    }
    
    @GetMapping("/name/{resourceName}/{pioneerId}")
    public Page<Resource> getByResourceNameCtrl(@PathVariable("pioneerId") Long pioneerId, @PathVariable("resourceName") String resourceName, Pageable pageable) {
    	
    	checkAuthorisation(pioneerId, pageable);    	
    	return ResourceService.getByResourceName(resourceName, pageable);
    }
    
    @GetMapping("/criticality/{resourceCriticality}/{pioneerId}")
    public Page<Resource> getByResourceCriticalityCtrl(@PathVariable("pioneerId") Long pioneerId, @PathVariable("resourceCriticality") Long resourceCriticality, Pageable pageable) {
        
    	checkAuthorisation(pioneerId, pageable);    	
    	return ResourceService.getByResourceCriticality(resourceCriticality, pageable);
    }
    
    @GetMapping("/quantityValue/{resourceQuantityValue}/{pioneerId}")
    public Page<Resource> getByResourceQuantityValueCtrl(@PathVariable("pioneerId") Long pioneerId, @PathVariable("resourceQuantityValue") float resourceQuantityValue, Pageable pageable) {
        
    	checkAuthorisation(pioneerId, pageable);    	
    	return ResourceService.getByResourceQuantityValue(resourceQuantityValue, pageable);
    }
    
    @GetMapping("/quantityDate/{resourceQuantityDate}/{pioneerId}")
    public Page<Resource> getByResourceQuantityDateCtrl(@PathVariable("pioneerId") Long pioneerId, @PathVariable("resourceQuantityDate") Date resourceQuantityDate, Pageable pageable) {
        
    	checkAuthorisation(pioneerId, pageable);    	
    	return ResourceService.getByResourceQuantityDate(resourceQuantityDate, pageable);
    }
    
    @GetMapping("/warningLevel/{resourceWarningLevel}/{pioneerId}")
    public Page<Resource> getByResourceWarningLevelCtrl(@PathVariable("pioneerId") Long pioneerId, @PathVariable("resourceWarningLevel") float resourceWarningLevel, Pageable pageable) {
        
    	checkAuthorisation(pioneerId, pageable);    	
    	return ResourceService.getByResourceWarningLevel(resourceWarningLevel, pageable);
    }
    
    @GetMapping("/emergencyLevel/{resourceEmergencyLevel}/{pioneerId}")
    public Page<Resource> getByResourceEmergencyLevelCtrl(@PathVariable("pioneerId") Long pioneerId, @PathVariable("resourceEmergencyLevel") float resourceEmergencyLevel, Pageable pageable) {
        
    	checkAuthorisation(pioneerId, pageable);    	
    	return ResourceService.getByResourceEmergencyLevel(resourceEmergencyLevel, pageable);
    }
    
    /* **********************************************
	 *                SAVE METHODS 
	 * **********************************************
	 * one method to save a resource */
	
	/* Request in order to save the data from the database about the resource */
	@PutMapping(value = "/save/{pioneerId}")
	@Transactional
	public Resource saveResourceCtrl(@PathVariable("pioneerId") Long pioneerId, @RequestBody Resource resource, Pageable pageable) {

    	checkAuthorisation(pioneerId, pageable);    	
		return ResourceService.saveOrUpdateResource(resource, pageable);	
 	}    
	
	/* **********************************************
	 *                DELETE METHODS 
	 * **********************************************
	 * one method to delete a resource */
	
	/* Request in order to save the data from the database about the resource */
	@DeleteMapping(value = "/delete/{resourceId}/{pioneerId}")
	@Transactional
	public Resource deleteResource(@PathVariable("pioneerId") Long pioneerId, @PathVariable("resourceId") Long resourceId, Pageable pageable) {

    	checkAuthorisation(pioneerId, pageable);    	
		return ResourceService.deleteResourceId(resourceId, pageable);
 	}    

}

