package com.sourcecodelab.scheduler.reminders.rest.controller;

import com.sourcecodelab.scheduler.reminders.rest.dto.ReminderDTO;
import com.sourcecodelab.scheduler.reminders.service.SchedulerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

public class SchedulerRestControllerTest {
    @Mock
    private SchedulerService schedulerService;

    @InjectMocks
    private SchedulerRestController schedulerRestController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void scheduleJob() {
        ReminderDTO reminder = ReminderDTO.builder().id(UUID.randomUUID().toString()).cron("0/40 * * * * ?").build();

        schedulerRestController.scheduleJob(reminder);

        verify(schedulerService).scheduleReminder(eq(reminder));
    }
}