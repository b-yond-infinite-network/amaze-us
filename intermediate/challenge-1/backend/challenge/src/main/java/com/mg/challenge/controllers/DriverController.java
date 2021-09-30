package com.mg.challenge.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mg.challenge.pojos.Driver;
import com.mg.challenge.services.DriverService;
import com.mg.challenge.services.MapValidationErrorMap;

@RestController
@CrossOrigin
@RequestMapping("api/driver")
public class DriverController {

	@Autowired
	private DriverService driverService;

	@Autowired
	private MapValidationErrorMap mapValidationErrorMap;

	@GetMapping("/{driverSSN}")
	public ResponseEntity<?> getDriverBySSN(@PathVariable String driverSSN) {
		Driver driverResponseObject = driverService.findDriverBySSN(driverSSN);
		return new ResponseEntity<Driver>(driverResponseObject, HttpStatus.OK);
	}

	@GetMapping("/all")
	public Iterable<Driver> getAllDrivers() {
		return driverService.findAllDrivers();
	}

	@PostMapping("")
	public ResponseEntity<?> createOrUpdateNewDriver(@Valid @RequestBody Driver driver, BindingResult result) {
		ResponseEntity<?> errorMap = mapValidationErrorMap.mapValidationService(result);
		if (errorMap != null)
			return errorMap;
		Driver driverResponseObject = driverService.saveOrUpdateDriver(driver);
		return new ResponseEntity<Driver>(driverResponseObject, HttpStatus.CREATED);
	}

	@DeleteMapping("/{driverSSN}")
	public ResponseEntity<?> deleteDriverBySSN(@PathVariable String driverSSN) {
		driverService.deleteDriverBySSN(driverSSN);
		return new ResponseEntity<Object>("Driver with SSN: " + driverSSN + " was deleted", HttpStatus.OK);
	}
}
