package com.sourcecodelab.scheduler.reminders.rest.controller;

import com.sourcecodelab.scheduler.reminders.rest.constants.APIConstants;
import com.sourcecodelab.scheduler.reminders.rest.dto.ReminderDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(APIConstants.PATH)
public interface SchedulerRestAPI {

    @PostMapping(path = APIConstants.CREATE_PATH)
    ResponseEntity scheduleJob(@RequestBody ReminderDTO reminder);
}
