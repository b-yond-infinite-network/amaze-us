package com.kepler.controller;

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
import com.kepler.model.Pioneer;
import com.kepler.service.PioneerService;

@RestController
@RequestMapping("/pioneer/*")
public class PioneerController {
	
    @Autowired
    private PioneerService PioneerService;
    
    @GetMapping("/all")
    public Page<Pioneer> getAllPioneersCtrl(Pageable pageable) {
        return PioneerService.getAllPioneers(pageable);
    }
    
    /* **********************************************
	 *                SAVE METHODS 
	 * **********************************************
	 * one method to save a resource */
	
	/* Request in order to save the data from the database about the resource */
    @PutMapping(value = "/save")
	@Transactional
	public Pioneer savePioneerCtrl(@RequestBody Pioneer pioneer, Pageable pageable) {
		
		Long userApplicationId = pioneer.getUserApplication().getUserApplicationId();
		return PioneerService.saveOrUpdatePioneer(pioneer, userApplicationId, pageable);	
 	}   
    
    @PutMapping(value = "/save/{userApplicationId}")
	@Transactional
	public Pioneer savePioneerWithUserApplicationIdCtrl(@RequestBody Pioneer pioneer, @PathVariable("userApplicationId") Long userApplicationId, Pageable pageable) {
		
		return PioneerService.saveOrUpdatePioneer(pioneer, userApplicationId, pageable);	
 	}   
	
	/* **********************************************
	 *                DELETE METHODS 
	 * **********************************************
	 * one method to delete a pioneer */
	
	/* Request in order to save the data from the database about the pioneer */
	@DeleteMapping(value = "/delete/{pioneerId}")
	@Transactional
	public Pioneer deletePioneerCtrl(@PathVariable("pioneerId") Long pioneerId, Pageable pageable) {

		return PioneerService.deletePioneerId(pioneerId, pageable);
 	}    

	@GetMapping("/authentification/{pioneerLogin}/{pioneerPassword}")
    public Page<Pioneer> authentificationCtrl(@PathVariable("pioneerLogin") String pioneerLogin, 
    		@PathVariable("pioneerPassword") String pioneerPassword, Pageable pageable) {
		
		Page<Pioneer> authentificationResult = PioneerService.getByPioneerLogin(pioneerLogin, pageable);
		if (authentificationResult.getTotalElements() != 1) {
			authentificationResult = PioneerService.getByPioneerEmail(pioneerLogin, pageable);
		}
		
		if (authentificationResult.getTotalElements() != 1) {
			throw new ResourceException("[PioneerController.java] authentificationCtrl", "The pioneer authentification is impossible because the login passed : " + pioneerLogin +
					" doesn't refer a pioneer in database with this as login neither as email address."
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(!authentificationResult.getContent().get(0).getPioneerPassword().equals(pioneerPassword)) {
			throw new ResourceException("[PioneerController.java] authentificationCtrl", "The pioneer authentification failed because the password passed isn't correct for the pioneer found !"
					, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
        return authentificationResult;
    }
	
}

