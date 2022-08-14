package com.beyond.microservice.schedule.service;

import java.util.Locale;
import javax.transaction.Transactional;

import com.beyond.microservice.schedule.bus.Bus;
import com.beyond.microservice.schedule.bus.BusRepository;
import com.beyond.microservice.schedule.driver.DriverRepository;
import com.beyond.microservice.schedule.entity.Schedule;
import com.beyond.microservice.schedule.exception.ResourceNotFoundException;
import com.beyond.microservice.schedule.repository.SchedultRepository;
import com.beyond.microservice.schedule.until.MessageKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleService {
    private final SchedultRepository scheduleRepository;
    
    private final BusRepository busRepository;
    
    private final DriverRepository driverRepository;
    private final MessageSource messageSource;
    
    public Schedule retrieveSchedule(final Long scheduleId) {
        if (scheduleId == null) {
            String message = messageSource.getMessage(MessageKey.SCHEDULE_ID_IS_NULL.value(),
                                                      null, Locale.getDefault());
            
            throw new NullPointerException(message);
        }
        Schedule schedule = scheduleRepository.findById(scheduleId).orElse(null);
        if (schedule == null) {
            String message = messageSource.getMessage(MessageKey.SCHEDULE_NOT_FOUND.value(),
                                                      new Object[]{scheduleId}, Locale.getDefault());
            
            throw new ResourceNotFoundException(message);
        }
        return schedule;
    }
    @Transactional
    public Schedule createSchedule(final Schedule schedule) {
        if (scheduleRepository.existsById(schedule.getId())) {
            log.info("Invoice id {} already exists - ignored", schedule.getId());
            //after retrive can the schedule be updated
            retrieveSchedule(schedule.getId());
        }
        return scheduleRepository.save(schedule);
       
    }
    
    public void createBus(final Bus bus) {
    }
}
