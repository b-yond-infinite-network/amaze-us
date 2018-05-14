package com.sourcecodelab.reminders.rest.controller;

import com.sourcecodelab.reminders.rest.client.email.SchedulerClient;
import com.sourcecodelab.reminders.rest.client.email.dto.ReminderTaskDTO;
import com.sourcecodelab.reminders.rest.dto.ReminderDTO;
import com.sourcecodelab.reminders.rest.dto.ReminderListDTO;
import com.sourcecodelab.reminders.service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@CrossOrigin(methods = { RequestMethod.OPTIONS, RequestMethod.DELETE, RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT })
public class ReminderRestController implements ReminderRestAPI {
    private final ReminderService reminderService;
    private final SchedulerClient schedulerClient;

    @Autowired
    public ReminderRestController(ReminderService reminderService, SchedulerClient schedulerClient) {
        this.reminderService = reminderService;
        this.schedulerClient = schedulerClient;
    }

    @Override
    public ResponseEntity<ReminderDTO> createReminder(@RequestBody @Valid ReminderDTO reminder) {
        reminder = reminderService.create(reminder);
        schedulerClient.schedule(ReminderTaskDTO.builder().cron(reminder.getCron()).id(reminder.getId()).build());
        reminder.setStatus("SCHEDULED");
        reminderService.update(reminder);
        return new ResponseEntity<>(reminder, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ReminderListDTO> getReminders() {
        return new ResponseEntity<>(ReminderListDTO.builder().reminders(reminderService.getAll()).build(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ReminderDTO> getReminder(@PathVariable @NotNull String reminderId) {
        return  new ResponseEntity<>(reminderService.read(reminderId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ReminderDTO> updateReminder(@RequestBody @Valid ReminderDTO reminder) {
        return new ResponseEntity<>(reminderService.update(reminder), HttpStatus.OK);
    }
}
