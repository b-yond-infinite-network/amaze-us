package com.kepler.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kepler.model.ResourceVariation;
import com.kepler.service.ResourceVariationService;

@RestController
@RequestMapping("/resourceVariation/*")
public class ResourceVariationController {
	
    @Autowired
    private ResourceVariationService ResourceVariationService;
    
    @GetMapping("/all")
    public Page<ResourceVariation> getAllResourceVariationsCtrl(Pageable pageable) {
        return ResourceVariationService.getAllResourceVariations(pageable);
    }
    
    @GetMapping("/id/{resourceVariationId}")
    public Page<ResourceVariation> getByResourceVariationIdCtrl(@PathVariable("resourceVariationId") Long resourceVariationId, Pageable pageable) {
        return ResourceVariationService.getByResourceVariationId(resourceVariationId, pageable);
    }
    
    @GetMapping("/value/{resourceVariationValue}")
    public Page<ResourceVariation> getByResourceVariationValueCtrl(@PathVariable("resourceVariationValue") float resourceVariationValue, Pageable pageable) {
        return ResourceVariationService.getByResourceVariationValue(resourceVariationValue, pageable);
    }
    
    @GetMapping("/date/{resourceVariationDate}")
    public Page<ResourceVariation> getByResourceVariationDateCtrl(@PathVariable("resourceVariationDate") Date resourceVariationDate, Pageable pageable) {
        return ResourceVariationService.getByResourceVariationDate(resourceVariationDate, pageable);
    }
    
    @GetMapping("/application/{resourceVariationApplication}")
    public Page<ResourceVariation> getByResourceVariationApplicationCtrl(@PathVariable("resourceVariationApplication") boolean resourceVariationApplication, Pageable pageable) {
        return ResourceVariationService.getByResourceVariationApplication(resourceVariationApplication, pageable);
    }
        
    /* **********************************************
	 *                SAVE METHODS 
	 * **********************************************
	 * one method to save a resourceVariation */
	
	/* Request in order to save the data from the database about the resource */
    @PutMapping(value = "/save")
	@Transactional
	public ResourceVariation saveResourceVariationCtrl(@RequestBody ResourceVariation resourceVariation,
			Pageable pageable) {
    	
    	Long resourceId = resourceVariation.getResource().getResourceId();
		return ResourceVariationService.saveOrUpdateResourceVariation(resourceVariation, resourceId, pageable);	
 	}
    
    @PutMapping(value = "/save/{resourceId}")
	@Transactional
	public ResourceVariation saveResourceVariationWithResourceIdCtrl(@RequestBody ResourceVariation resourceVariation, @PathVariable("resourceId") Long resourceId,
			Pageable pageable) {

		return ResourceVariationService.saveOrUpdateResourceVariation(resourceVariation, resourceId, pageable);	
 	}
	
	/* **********************************************
	 *                DELETE METHODS 
	 * **********************************************
	 * one method to delete a resourceVariation */
	
	/* Request in order to save the data from the database about the resource */
	@DeleteMapping(value = "/delete/{resourceVariationId}")
	@Transactional
	public ResourceVariation deleteResource(@PathVariable("resourceVariationId") Long resourceVariationId,
			Pageable pageable) {

		return ResourceVariationService.deleteResourceVariationId(resourceVariationId, pageable);
 	}  
}

