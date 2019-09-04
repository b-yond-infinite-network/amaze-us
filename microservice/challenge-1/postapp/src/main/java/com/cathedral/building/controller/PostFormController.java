package com.cathedral.building.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cathedral.building.logging.CustomException;
import com.cathedral.building.logging.CustomExceptions;
import com.cathedral.building.model.Form;
import com.cathedral.building.service.BuildingService;

@RestController
@CrossOrigin
public class PostFormController {

	private static final Logger logger = LoggerFactory.getLogger(PostFormController.class);

	@Autowired
	BuildingService buildingService;

	@SuppressWarnings("unchecked")
	@PostMapping(path="/save", consumes="application/json")
	public <T> ResponseEntity<T> saveForm(@RequestBody Form form){

		long startTime = System.currentTimeMillis();

		CustomExceptions customExceptions = new CustomExceptions();

		if(null != form.getName() && !form.getName().isEmpty() 
				&& null != form.getEmail() && !form.getEmail().isEmpty()){
			
			try{
				buildingService.saveForm(form.getName(), form.getEmail(), form.getDescription());
			} catch(Exception e){
				logger.error("Backend Error: "+e.getMessage());
				CustomException customException = new CustomException("1002", "ERROR", e.getMessage());
				customExceptions.setCustomException(customException);
				return (ResponseEntity<T>) new ResponseEntity<CustomExceptions>(customExceptions, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			logger.error("Mandatory value name or email not passed in request");
			CustomException customException = new CustomException("1001", "ERROR", "Mandatory fields missing");
			customExceptions.setCustomException(customException);
			return (ResponseEntity<T>) new ResponseEntity<CustomExceptions>(customExceptions, HttpStatus.BAD_REQUEST);
		}

		long endTime = System.currentTimeMillis();
		long totTime = endTime - startTime;
		logger.info("Total time taken: "+totTime);

		return (ResponseEntity<T>) new ResponseEntity<String>("Success", HttpStatus.OK);
	}
}