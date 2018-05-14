package com.sourcecodelab.scheduler.reminders.rest.controller;

import com.sourcecodelab.scheduler.reminders.rest.dto.ReminderDTO;
import com.sourcecodelab.scheduler.reminders.service.SchedulerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@CrossOrigin(methods = { RequestMethod.OPTIONS, RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
public class SchedulerRestController implements SchedulerRestAPI {
    private final SchedulerService schedulerService;

    @Autowired
    public SchedulerRestController(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @Override
    public ResponseEntity scheduleJob(@RequestBody ReminderDTO reminder) {
        schedulerService.scheduleReminder(reminder);
        reminder.setStatus("SCHEDULED");
        return new ResponseEntity<>(reminder, HttpStatus.ACCEPTED);
    }
}
