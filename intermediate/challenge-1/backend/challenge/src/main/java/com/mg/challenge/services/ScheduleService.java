package com.mg.challenge.services;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mg.challenge.exceptions.SchedulePKException;
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

	public Schedule findScheduleByPK(@Valid SchedulePK pk) {
		Optional<Schedule> optionalRef = scheduleRepository.findById(pk);
		if(optionalRef.isPresent())
			return optionalRef.get();
		throw new SchedulePKException("Schedule of Driver with SSN: " + pk.getDriverSSN() + " & Bus ID: " + pk.getBusID() + " not found");
	}
	
	public Iterable<Schedule> findScheduleByBusID(Integer busID) {
		return scheduleRepository.findScheduleByBusID(busID);
	}
	
	public Iterable<Schedule> findScheduleByDriverSSN(String ssn) {
		return scheduleRepository.findScheduleByDriverSSN(ssn);
	}
	
	public void deleteScheduleByPK(SchedulePK pk) {
		Schedule schedule = pk == null ? null : scheduleRepository.getById(pk);
		scheduleRepository.delete(schedule);
    }

	public Schedule saveOrUpdateSchedule(@Valid Schedule schedule) {
		schedule.setPrimaryKey(schedule.getPrimaryKey());
		return scheduleRepository.save(schedule);
	}

}
