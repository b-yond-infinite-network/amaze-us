package com.ms.reminder.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ms.reminder.model.Reminder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path="/v1/reminderapp")
@Tag(name = "Reminder App", description = "Reminder Application API")
public interface AppController {
	
	@Operation(summary="Create a Reminder in the Reminder Application")
	@PostMapping(consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Reminder> createReminder(@RequestBody Reminder reminder);
	
	@Operation(summary="Update a Reminder by Reminder Id")
	@PutMapping(path="/{id}",consumes= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Reminder> editReminder(@PathVariable ("id") Long id,@RequestBody Reminder reminder);
	
	@Operation(summary="Delete a Reminder from the Reminder Application")
	@DeleteMapping(path="/{id}")
	public void deleteReminder(@PathVariable ("id") Long id );
	
	@Operation(summary="Find a Reminder by its Identifier")
	@GetMapping(path="/{id}",produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Reminder> findReminder(@PathVariable ("id") Long id );
	
	@Operation(summary="Find all Reminders saved in the Application")
	@GetMapping(produces= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Reminder>> findAllReminders();
	
	@Operation(summary="Find all Reminders between a certain Date/Time Interval")
	@GetMapping(path="/{from}/{to}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Reminder>> findReminderRange(@PathVariable("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
			@PathVariable("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to);
	
	@Operation(summary="Count the number of Reminders")
	@GetMapping(path="/count",produces=MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> countReminder();

}
