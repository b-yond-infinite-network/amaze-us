package com.mg.challenge.services;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mg.challenge.pojos.Schedule;
import com.mg.challenge.pojos.SchedulePK;
import com.mg.challenge.repositories.ScheduleRepository;

@Service
public class ScheduleService {

	@Autowired
	private ScheduleRepository scheduleRepository;

	public Iterable<Schedule> findAllSchedules() {
		return scheduleRepository.findAll();
	}

//	public Iterable<Schedule> findScheduleByBusID(Integer id) {
//		
//	}
//	
//	public Iterable<Schedule> findScheduleByDriverSSN(String ssn) {
//		
//	}
	
	public void deleteScheduleByID(SchedulePK pk) {
		Schedule schedule = pk == null ? null : scheduleRepository.getById(pk);
		scheduleRepository.delete(schedule);
    }

	public Schedule saveOrUpdateSchedule(@Valid Schedule schedule) {
		schedule.setPrimaryKey(schedule.getPrimaryKey());
		return scheduleRepository.save(schedule);
	}

}
