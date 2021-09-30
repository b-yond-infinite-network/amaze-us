package com.mg.challenge.services;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mg.challenge.exceptions.ScheduleException;
import com.mg.challenge.pojos.Schedule;
import com.mg.challenge.repositories.ScheduleRepository;

@Service
public class ScheduleService {

	@Autowired
	private ScheduleRepository scheduleRepository;

	public Iterable<Schedule> findAllSchedules() {
		return scheduleRepository.findAll();
	}

	public Schedule findScheduleById(Integer id) {
		Optional<Schedule> optionalRef = scheduleRepository.findById(id);
		if(optionalRef.isPresent())
			return optionalRef.get();
		throw new ScheduleException("Schedule with ID: " + id + " not found");
	}
	
	public Iterable<Schedule> findScheduleByBusID(Integer busID) {
		return scheduleRepository.findSchedulesByBusID(busID);
	}
	
	public Iterable<Schedule> findScheduleByDriverSSN(String ssn) {
		return scheduleRepository.findSchedulesByDriverSSN(ssn);
	}
	
	public Iterable<Schedule> findWeeklyScheduleByBusID(Integer busID, Date day) {
		GregorianCalendar start = (GregorianCalendar) Calendar.getInstance();
		start.setTime(day);
		Date from = start.getTime();
		
		GregorianCalendar end = (GregorianCalendar) start.clone();
		end.add(Calendar.DAY_OF_MONTH, 6);
		Date to = end.getTime();
		
		return scheduleRepository.findSchedulesByBusID(busID, from, to);
	}
	
	public Iterable<Schedule> findWeeklyScheduleByDriverSSN(String ssn, Date day) {
		GregorianCalendar start = (GregorianCalendar) Calendar.getInstance();
		start.setTime(day);
		Date from = start.getTime();
		
		GregorianCalendar end = (GregorianCalendar) start.clone();
		end.add(Calendar.DAY_OF_MONTH, 6);
		Date to = end.getTime();
		
		return scheduleRepository.findSchedulesByDriverSSN(ssn, from, to);
	}
	
	public Iterable<Schedule> findWeeklyScheduleByDate(Date date){
		GregorianCalendar start = (GregorianCalendar) Calendar.getInstance();
		start.setTime(date);
		Date from = start.getTime();
		
		GregorianCalendar end = (GregorianCalendar) start.clone();
		end.add(Calendar.DAY_OF_MONTH, 6);
		Date to = end.getTime();
		
		Iterable<Schedule> schedules = scheduleRepository.findSchedulesBetweenDates(from, to);
		return schedules;
	}
	
	public void deleteScheduleById(Integer id) {
		Optional<Schedule> op = scheduleRepository.findById(id);
		if(op.isPresent()) {
			Schedule schedule = op.get();
			scheduleRepository.delete(schedule);
		}
    }

	public Schedule saveOrUpdateSchedule(@Valid Schedule schedule) {
		return scheduleRepository.save(schedule);
	}

}
