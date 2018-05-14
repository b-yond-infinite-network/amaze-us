package com.sourcecodelab.reminders.rest.controller;

import com.sourcecodelab.reminders.rest.dto.ReminderDTO;
import com.sourcecodelab.reminders.rest.dto.ReminderListDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.sourcecodelab.reminders.rest.constants.APIConstants.*;

@RequestMapping(PATH)
public interface ReminderRestAPI {

    @PostMapping(path = CREATE_PATH)
    ResponseEntity<ReminderDTO> createReminder(@RequestBody ReminderDTO reminder);

    @GetMapping(path = GET_ALL_PATH)
    ResponseEntity<ReminderListDTO> getReminders();

    @GetMapping(path = GET_REMINDER_PATH)
    ResponseEntity<ReminderDTO> getReminder(@PathVariable(REMINDER_ID) String reminderId);

    @PostMapping(path = UPDATE_REMINDER_PATH)
    ResponseEntity<ReminderDTO> updateReminder(@RequestBody ReminderDTO reminder);
}
