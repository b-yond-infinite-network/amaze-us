package com.sourcecodelab.reminders.rest.client.email;

import com.google.common.annotations.VisibleForTesting;
import com.sourcecodelab.reminders.rest.client.email.dto.ReminderTaskDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class SchedulerClient {
    @Value("${reminder.service.url:http://localhost:8082/api/scheduler/v1}")
    private String reminderServiceUrl;
    @Value("${reminder.service.schedule.task.path:/reminder}")
    private String sendReminderServicePath;

    public ReminderTaskDTO schedule(ReminderTaskDTO task) {
        return new RestTemplate().postForEntity(getUrlToScheduleTask(), task, ReminderTaskDTO.class).getBody();
    }

    @VisibleForTesting
    protected String getUrlToScheduleTask() {
        return UriComponentsBuilder.fromUriString(reminderServiceUrl).path(sendReminderServicePath).build().toUriString();
    }
}
