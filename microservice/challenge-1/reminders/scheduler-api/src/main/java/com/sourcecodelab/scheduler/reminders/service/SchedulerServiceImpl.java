package com.sourcecodelab.scheduler.reminders.service;

import com.sourcecodelab.scheduler.reminders.jobs.ReminderJob;
import com.sourcecodelab.scheduler.reminders.rest.dto.ReminderDTO;
import com.sourcecodelab.scheduler.reminders.service.exceptions.SchedulerServiceException;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Service;

import static com.sourcecodelab.scheduler.reminders.service.exceptions.ErrorCode.SCHEDULER_ERROR;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

@Slf4j
@Service
public class SchedulerServiceImpl implements SchedulerService {
    private final SpringBeanJobFactory springBeanJobFactory;
    private final SchedulerFactoryBean schedulerFactoryBean;

    @Autowired
    public SchedulerServiceImpl(SpringBeanJobFactory springBeanJobFactory, SchedulerFactoryBean schedulerFactoryBean) {
        this.springBeanJobFactory = springBeanJobFactory;
        this.schedulerFactoryBean = schedulerFactoryBean;
    }

    @Override
    public void scheduleReminder(ReminderDTO reminder) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            String reminderId = reminder.getId();
            // define the job and tie it to our ReminderJob class
            JobDetail job = newJob(ReminderJob.class)
                    .withIdentity(reminderId, "reminder")
                    .build();
            job.getJobDataMap().put(ReminderJob.REMINDER_ID, reminder.getId());

            // Trigger the job to run now, and then repeat every 40 seconds
            Trigger trigger = newTrigger()
                    .withIdentity(reminderId, "reminder")
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule(reminder.getCron()))
                    .build();

            // Tell quartz to schedule the job using our trigger
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            log.error("An error occurred while scheduling a job.", e);
            throw new SchedulerServiceException(SCHEDULER_ERROR);
        }

    }
}
