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

import com.mg.challenge.pojos.Schedule;
import com.mg.challenge.pojos.SchedulePK;
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

	@GetMapping("/{schedulePK}")
	public ResponseEntity<?> getScheduleBySSN(@PathVariable SchedulePK schedulePK) {
		Schedule scheduleResponseObject = scheduleService.findScheduleByPK(schedulePK);
		return new ResponseEntity<Schedule>(scheduleResponseObject, HttpStatus.OK);
	}

	@GetMapping("/all")
	public Iterable<Schedule> getAllSchedules() {
		return scheduleService.findAllSchedules();
	}

	@PostMapping("")
	public ResponseEntity<?> createOrUpdateNewSchedule(@Valid @RequestBody Schedule schedule, BindingResult result) {
		ResponseEntity<?> errorMap = mapValidationErrorMap.mapValidationService(result);
		if (errorMap != null)
			return errorMap;
		Schedule scheduleResponseObject = scheduleService.saveOrUpdateSchedule(schedule);
		return new ResponseEntity<Schedule>(scheduleResponseObject, HttpStatus.CREATED);
	}

	@DeleteMapping("/{schedulePK}")
	public ResponseEntity<?> deleteScheduleBySSN(@PathVariable SchedulePK schedulePK) {
		scheduleService.deleteScheduleByPK(schedulePK);
		return new ResponseEntity<Object>("Schedule with ID: " + schedulePK + " was deleted", HttpStatus.OK);
	}
}
