package com.beyond.microservice.schedule.controller;

import java.util.List;

import com.beyond.microservice.schedule.entity.Schedule;
import com.beyond.microservice.schedule.model.ScheduleDto;
import com.beyond.microservice.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/schedule",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;
    
    @PostMapping
    public ScheduleDto createSchedule(@RequestBody final Schedule schedule) {
        return scheduleService.createSchedule(schedule);
    }
    
    @GetMapping("/bus/{busId}")
    public List<ScheduleDto> getBusWeeklySchedule(@PathVariable(value = "busId") final Long busId) {
        return scheduleService.getBusWeeklySchedule(busId);
    }
    
    @GetMapping("/driver/{driverId}")
    public List<ScheduleDto> getDriverWeeklySchedule(@PathVariable(value = "driverId") final Long driverId) {
        return scheduleService.getDriverWeeklySchedule(driverId);
    }
    
    
    
}
