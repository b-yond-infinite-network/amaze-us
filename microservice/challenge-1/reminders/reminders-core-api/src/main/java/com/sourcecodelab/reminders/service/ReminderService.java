package com.sourcecodelab.reminders.service;

import com.sourcecodelab.reminders.rest.dto.ReminderDTO;

import java.util.List;

public interface ReminderService {
    ReminderDTO create(ReminderDTO reminderDTO);
    ReminderDTO update(ReminderDTO reminderDTO);
    ReminderDTO read(String externalId);
    List<ReminderDTO> getAll();
}
