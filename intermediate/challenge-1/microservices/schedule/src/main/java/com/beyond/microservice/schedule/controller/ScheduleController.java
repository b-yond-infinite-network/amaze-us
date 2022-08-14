package com.beyond.microservice.schedule.controller;

import com.beyond.microservice.schedule.entity.Schedule;
import com.beyond.microservice.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
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
    public Schedule createSchedule(@RequestBody final Schedule schedule) {
        return scheduleService.createSchedule(schedule);
    }
}
