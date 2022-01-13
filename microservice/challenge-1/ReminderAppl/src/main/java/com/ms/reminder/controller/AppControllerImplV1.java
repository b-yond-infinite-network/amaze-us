package com.ms.reminder.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ms.reminder.config.exception.ReminderNotFound;
import com.ms.reminder.model.Reminder;
import com.ms.reminder.service.AppService;


@RestController
public class AppControllerImplV1 implements AppController{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AppControllerImplV1.class);

	@Autowired
	private AppService reminderAppService;
	
	public ResponseEntity<Reminder> createReminder(@RequestBody Reminder reminder) {
		
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);  
		Reminder savedReminder = reminderAppService.createReminder(reminder);
		LOGGER.info("Data Saved {}",savedReminder);
        return new ResponseEntity<Reminder>(savedReminder,headers,HttpStatus.CREATED); 
	}
	
	public ResponseEntity<Reminder> editReminder(@PathVariable ("id") Long id,@RequestBody Reminder reminder) {
		
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);  
		Reminder savedReminder = reminderAppService.editReminder(id,reminder);
		LOGGER.info("Data edited {}",savedReminder);
        return new ResponseEntity<Reminder>(savedReminder,headers,HttpStatus.OK); 
	}
	
	public void deleteReminder(@PathVariable ("id") Long id ) {
		
		reminderAppService.deleteReminder(id);
	}
	
	public ResponseEntity<Reminder> findReminder(@PathVariable ("id") Long id ) {
		
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        try {
        	Reminder reminder = reminderAppService.findReminder(id);
            LOGGER.info("Data Returned {}",reminder);
            return new ResponseEntity<Reminder>(reminder,headers,HttpStatus.FOUND); 
            
        }catch(ReminderNotFound ex) {
        	Reminder reminderNotFound=Reminder.builder()
        			.id(id).build();
        	 return new ResponseEntity<Reminder>(reminderNotFound,headers,HttpStatus.NOT_FOUND); 
        }
        
	}
	
	public ResponseEntity<List<Reminder>> findAllReminders() {
		
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);  
        List<Reminder> reminderList = reminderAppService.findAllReminders();
        LOGGER.info("Data Returned {}",reminderList);
        
		return new ResponseEntity<List<Reminder>>(reminderList,headers,HttpStatus.FOUND);
		
	}
	
	public ResponseEntity<List<Reminder>> findReminderRange(@PathVariable("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
			@PathVariable("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
		
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);  
        List<Reminder> reminderList = reminderAppService.findReminderInRange(from,to);
        LOGGER.info("Data Returned {}",reminderList);
		
        return new ResponseEntity<List<Reminder>>(reminderList,headers,HttpStatus.FOUND);
	}
	
	public ResponseEntity<String> countReminder() {
		
		HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.TEXT_PLAIN_VALUE);  
        Integer countOfReminder = reminderAppService.countReminders();;
        LOGGER.info("Data Returned {}",countOfReminder);
		return new ResponseEntity<String>(countOfReminder.toString(),headers,HttpStatus.OK);
	}
}
