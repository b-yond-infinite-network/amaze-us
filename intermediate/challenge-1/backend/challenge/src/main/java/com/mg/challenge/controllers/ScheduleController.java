package com.mg.challenge.controllers;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mg.challenge.pojos.Schedule;
import com.mg.challenge.services.MapValidationErrorMap;
import com.mg.challenge.services.ScheduleService;

@RestController
@CrossOrigin
@RequestMapping("api/schedule")
public class ScheduleController {

	@Autowired
	private ScheduleService scheduleService;

	@Autowired
	private MapValidationErrorMap mapValidationErrorMap;

	@GetMapping("/all")
	public Iterable<Schedule> getAllSchedules() {
		return scheduleService.findAllSchedules();
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getScheduleByID(@PathVariable Integer id) {
		Schedule scheduleResponseObject = scheduleService.findScheduleById(id);
		return new ResponseEntity<Schedule>(scheduleResponseObject, HttpStatus.OK);
	}

	@GetMapping("/busID")
	public ResponseEntity<?> getScheduleByBusID(@RequestParam Integer busID) {
		Iterable<Schedule> scheduleResponseObject = scheduleService.findScheduleByBusID(busID);
		return new ResponseEntity<Iterable<Schedule>>(scheduleResponseObject, HttpStatus.OK);
	}

	@GetMapping("/busID/Weekly")
	public ResponseEntity<?> getWeeklyScheduleByBusID(@RequestParam Integer busID, @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date day) {
		Iterable<Schedule> scheduleResponseObject = scheduleService.findWeeklyScheduleByBusID(busID, day);
		return new ResponseEntity<Iterable<Schedule>>(scheduleResponseObject, HttpStatus.OK);
	}

	@GetMapping("/driverSSN")
	public ResponseEntity<?> getScheduleByDriverSSN(@RequestParam String driverSSN) {
		Iterable<Schedule> scheduleResponseObject = scheduleService.findScheduleByDriverSSN(driverSSN);
		return new ResponseEntity<Iterable<Schedule>>(scheduleResponseObject, HttpStatus.OK);
	}

	@GetMapping("/driverSSN/Weekly")
	public ResponseEntity<?> getWeeklyScheduleByDriverSSN(@RequestParam String driverSSN, @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date day) {
		Iterable<Schedule> scheduleResponseObject = scheduleService.findWeeklyScheduleByDriverSSN(driverSSN, day);
		return new ResponseEntity<Iterable<Schedule>>(scheduleResponseObject, HttpStatus.OK);
	}

	@GetMapping("/date/Weekly")
	public ResponseEntity<?> getWeeklySchedules(@RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date date) {
		Iterable<Schedule> scheduleResponseObject = scheduleService.findWeeklyScheduleByDate(date);
		return new ResponseEntity<Iterable<Schedule>>(scheduleResponseObject, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> createOrUpdateNewSchedule(@Valid @RequestBody Schedule schedule, BindingResult result) {
		ResponseEntity<?> errorMap = mapValidationErrorMap.mapValidationService(result);
		if (errorMap != null)
			return errorMap;
		Schedule scheduleResponseObject = scheduleService.saveOrUpdateSchedule(schedule);
		return new ResponseEntity<Schedule>(scheduleResponseObject, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteScheduleByID(@PathVariable Integer id) {
		scheduleService.deleteScheduleById(id);
		return new ResponseEntity<Object>("Schedule with ID: " + id + " was deleted", HttpStatus.OK);
	}
}
