package com.kepler.controller;

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

import com.kepler.model.UserApplication;
import com.kepler.service.UserApplicationService;

@RestController
@RequestMapping("/userApplication/*")
public class UserApplicationController {
	
    @Autowired
    private UserApplicationService UserApplicationService;
    
    @GetMapping("/all")
    public Page<UserApplication> getAllUserApplicationsCtrl(Pageable pageable) {
        return UserApplicationService.getAllUserApplications(pageable);
    }
    
    @GetMapping("/id/{userApplicationId}")
    public Page<UserApplication> getByUserApplicationIdCtrl(@PathVariable("userApplicationId") Long userApplicationId, Pageable pageable) {
        return UserApplicationService.getByUserApplicationId(userApplicationId, pageable);
    }
    
    @GetMapping("/profile/{userApplicationProfile}")
    public Page<UserApplication> getByUserApplicationProfileCtrl(@PathVariable("userApplicationProfile") String userApplicationProfile, Pageable pageable) {
        return UserApplicationService.getByUserApplicationProfile(userApplicationProfile, pageable);
    }
    
    /* **********************************************
	 *                SAVE METHODS 
	 * **********************************************
	 * one method to save a resource */
	
	/* Request in order to save the data from the database about the resource */
	@PutMapping(value = "/save")
	@Transactional
	public UserApplication saveResourceCtrl(@RequestBody UserApplication userApplication, Pageable pageable) {

		return UserApplicationService.saveOrUpdateUserApplication(userApplication, pageable);	
 	}    
	
	/* **********************************************
	 *                DELETE METHODS 
	 * **********************************************
	 * one method to delete a resource */
	
	/* Request in order to save the data from the database about the resource */
	@DeleteMapping(value = "/delete/{userApplicationId}")
	@Transactional
	public UserApplication deleteUserApplicationCtrl(@PathVariable("userApplicationId") Long userApplicationId, Pageable pageable) {

		return UserApplicationService.deleteUserApplicationId(userApplicationId, pageable);
 	}    

}

