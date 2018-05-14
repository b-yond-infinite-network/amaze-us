package com.sourcecodelab.scheduler.reminders.service;

import com.sourcecodelab.scheduler.reminders.rest.dto.ReminderDTO;

public interface SchedulerService {
    void scheduleReminder(ReminderDTO reminder);
}
