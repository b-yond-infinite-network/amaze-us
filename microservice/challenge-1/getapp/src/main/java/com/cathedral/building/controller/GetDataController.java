package com.cathedral.building.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cathedral.building.logging.CustomException;
import com.cathedral.building.logging.CustomExceptions;
import com.cathedral.building.model.Form;
import com.cathedral.building.service.BuildingService;

@RestController
@CrossOrigin
public class GetDataController {

	private static final Logger logger = LoggerFactory.getLogger(GetDataController.class);

	@Autowired
	BuildingService buildingService;

	@SuppressWarnings("unchecked")
	@GetMapping(path="/getdata", produces="application/json")
	public @ResponseBody <T> ResponseEntity<T> getDbData(){

		long startTime = System.currentTimeMillis();

		List<Form> listForm = new ArrayList<Form>();
		CustomExceptions customExceptions = new CustomExceptions();

		try{
			listForm = buildingService.getFormData();
		} catch(Exception e){
			logger.error("Backend Error: "+e.getMessage());
			CustomException customException = new CustomException("1002", "ERROR", e.getMessage());
			customExceptions.setCustomException(customException);
			return (ResponseEntity<T>) new ResponseEntity<CustomExceptions>(customExceptions, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		long endTime = System.currentTimeMillis();
		long totTime = endTime - startTime;
		logger.info("Total time taken: "+totTime);

		return (ResponseEntity<T>) new ResponseEntity<List<Form>>(listForm, HttpStatus.OK);
	}
}