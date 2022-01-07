package com.ms.reminder.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ms.reminder.model.Reminder;
import com.ms.reminder.repository.AppRepository;

@Service
public class AppService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AppService.class);
	
	@Autowired
	protected AppRepository apprepository;
	
	public Integer countReminders() {
		
		long numberOfReminders = apprepository.count();
		LOGGER.info("Number of Reminders : {}",numberOfReminders);
		return new Long(numberOfReminders).intValue();
	}
	
	public Reminder createReminder(Reminder reminder) {
		
		Reminder savedReminder = apprepository.save(reminder);
		LOGGER.info("New Reminder with name {} saved",savedReminder.getName());
		return savedReminder;
	}
	
	public Reminder editReminder(Long id,Reminder reminder) {
		
		Optional<Reminder> reminderFound = apprepository.findById(id);
		if(reminderFound.isPresent()) {
			
			LOGGER.info("Reminder with name {} and Id: {} found",reminderFound.get().getName(),reminderFound.get().getId());
			reminder.setId(reminderFound.get().getId());
			Reminder editedReminder = apprepository.save(reminder);
			LOGGER.info("Updated Reminder with name {} and Id: {} saved",editedReminder.getName(),editedReminder.getId());
			return editedReminder;
		}else {
			
			LOGGER.info("Reminder with  Id: {} not found",id);
			return null;
		}
			
	}
	
	public void deleteReminder(Long id) {
		
		Optional<Reminder> foundReminder = apprepository.findById(id);
		LOGGER.info("Reminder with ID : {} ",foundReminder);
		if(foundReminder.isPresent()) {
			apprepository.deleteById(id);
			LOGGER.info("Reminder with ID : {} deleted",foundReminder);
		}
		
	}
	
	public List<Reminder> findAllReminders() {
		
		List<Reminder> reminderList = apprepository.findAll();
		return reminderList;
		
	}
	
	public Reminder findReminder(Long id) {
		
		Optional<Reminder> reminder = apprepository.findById(id);
		return reminder.get();
		
	}
	
	public List<Reminder> findReminderInRange(LocalDateTime fromTime,LocalDateTime toTime) {
		LOGGER.info("Find all Reminder(s) between from-date-time {} and to-date-time {} ",fromTime,toTime);
		List<Reminder> reminderList = apprepository.getRemindersWithinTimeRange(fromTime,toTime);
		return reminderList;
		
	}

}
