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

import com.mg.challenge.pojos.Bus;
import com.mg.challenge.services.BusService;
import com.mg.challenge.services.MapValidationErrorMap;

@RestController
@CrossOrigin
@RequestMapping("api/bus")
public class BusController {

	@Autowired
	private BusService busService;

	@Autowired
	private MapValidationErrorMap mapValidationErrorMap;

	@GetMapping("/{busID}")
	public ResponseEntity<?> getBusBySSN(@PathVariable Integer busID) {
		Bus busResponseObject = busService.findBusByID(busID);
		return new ResponseEntity<Bus>(busResponseObject, HttpStatus.OK);
	}

	@GetMapping("/all")
	public Iterable<Bus> getAllBuss() {
		return busService.findAllBuss();
	}

	@PostMapping
	public ResponseEntity<?> createOrUpdateNewBus(@Valid @RequestBody Bus bus, BindingResult result) {
		ResponseEntity<?> errorMap = mapValidationErrorMap.mapValidationService(result);
		if (errorMap != null)
			return errorMap;
		Bus busResponseObject = busService.saveOrUpdateBus(bus);
		return new ResponseEntity<Bus>(busResponseObject, HttpStatus.CREATED);
	}

	@DeleteMapping("/{busID}")
	public ResponseEntity<?> deleteBusBySSN(@PathVariable Integer busID) {
		busService.deleteBusByID(busID);
		return new ResponseEntity<Object>("Bus with ID: " + busID + " was deleted", HttpStatus.OK);
	}
}
