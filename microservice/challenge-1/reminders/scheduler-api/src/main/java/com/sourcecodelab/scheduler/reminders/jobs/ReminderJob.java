package com.sourcecodelab.scheduler.reminders.jobs;

import com.sourcecodelab.scheduler.reminders.rest.client.email.EmailClient;
import com.sourcecodelab.scheduler.reminders.rest.client.email.dto.MailDTO;
import com.sourcecodelab.scheduler.reminders.rest.client.remindercore.ReminderCoreClient;
import com.sourcecodelab.scheduler.reminders.rest.client.remindercore.dto.ReminderInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReminderJob implements Job {
    public static final String REMINDER_ID = "reminderId";

    @Value("${reminder.email.originator:siriman0701@gmail.com}")
    private String originator;
    @Value("${reminder.email.subject:Reminder}")
    private String subject;

    @Autowired
    private ReminderCoreClient reminderCoreClient;
    @Autowired
    private EmailClient emailClient;

    @Override
    public void execute(JobExecutionContext context){
        log.info("Processing a new reminder");
        String reminderId = context.getJobDetail().getJobDataMap().getString(REMINDER_ID);
        log.debug("The reminder id is {}.", reminderId);
        ReminderInfoDTO reminderInfo = reminderCoreClient.getReminderInfo(reminderId);
        emailClient.sendEmail(MailDTO.builder().sender(originator).recipient(reminderInfo.getRecipient()).subject(subject).text(reminderInfo.getText()).build());
        log.info("Reminder with id={} successfully processed.", reminderId);
    }
}
