package com.sourcecodelab.scheduler.reminders.rest.client.remindercore;

import com.sourcecodelab.scheduler.reminders.rest.client.remindercore.dto.ReminderInfoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ReminderCoreClient {

    @Value("${reminder.core.service.url:http://localhost:8080/api/reminders/v1}")
    private String reminderServiceUrl;
    @Value("${reminder.core.service.get.reminder.path:/reminder/}")
    private String getReminderServicePath;

    public ReminderInfoDTO getReminderInfo(String reminderId) {
        return new RestTemplate().getForEntity(UriComponentsBuilder.fromUriString(reminderServiceUrl).path(getReminderServicePath.concat(reminderId)).build().toUriString(), ReminderInfoDTO.class).getBody();
    }
}
