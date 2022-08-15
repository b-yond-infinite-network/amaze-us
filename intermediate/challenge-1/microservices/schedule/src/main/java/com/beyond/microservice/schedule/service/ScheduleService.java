package com.beyond.microservice.schedule.service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import com.beyond.microservice.schedule.bus.Bus;
import com.beyond.microservice.schedule.bus.BusRepository;
import com.beyond.microservice.schedule.driver.Driver;
import com.beyond.microservice.schedule.driver.DriverRepository;
import com.beyond.microservice.schedule.entity.Schedule;
import com.beyond.microservice.schedule.exception.ResourceNotFoundException;
import com.beyond.microservice.schedule.model.ScheduleDto;
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
    public ScheduleDto createSchedule(final Schedule schedule) {
        if (busRepository.findById(schedule.getBus().getId()).isEmpty()) {
            log.info("bus does not exists", schedule.getBus());
            throw new NullPointerException("Bus does not exist");
        }
        
        if (driverRepository.findById(schedule.getDriver().getId()).isEmpty()) {
            log.info("driver does not exists", schedule.getDriver());
            throw new NullPointerException("driver does not exist");
        }
        return modelMap(scheduleRepository.save(schedule));
    }
    
    public void createBus(final Bus bus) {
        busRepository.save(bus);
    }
    
    public List<ScheduleDto> getBusWeeklySchedule(final Long busId) {
        
        return scheduleRepository.findByBusId(busId).stream().map(this::modelMap).collect(
            Collectors.toList());
    }
    
    public List<ScheduleDto> getDriverWeeklySchedule(final Long driverId) {
        return scheduleRepository.findByDriverId(driverId).stream().map(this::modelMap).collect(
            Collectors.toList());
    }
    
    public void createDriver(final Driver driver) {
        driverRepository.save(driver);
    }
    
    private ScheduleDto modelMap(Schedule schedule) {
        final Driver driver = driverRepository.findById(schedule.getId()).get();
        return ScheduleDto.builder()
                       .id(schedule.getId())
                       .busId(schedule.getBus().getId())
                       .driverName(driver.getFirstname() + " ".concat(driver.getLastName()))
                       .day(schedule.getDay())
                       .start(schedule.getStart())
                       .end(schedule.getEnd())
                       .build();
    }
}
