package com.sourcecodelab.scheduler.reminders.rest.client.remindercore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReminderInfoDTO {
    private String sender;
    private String recipient;
    private String text;
    private String cron;
}
