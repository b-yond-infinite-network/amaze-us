package com.sourcecodelab.reminders.rest.controller;

import com.sourcecodelab.reminders.rest.client.email.SchedulerClient;
import com.sourcecodelab.reminders.rest.client.email.dto.ReminderTaskDTO;
import com.sourcecodelab.reminders.rest.dto.ReminderDTO;
import com.sourcecodelab.reminders.service.ReminderService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReminderRestControllerTest {
    @Mock
    private ReminderService reminderService;
    @Mock
    private SchedulerClient schedulerClient;
    @InjectMocks
    private ReminderRestController reminderRestController;
    @Captor
    private ArgumentCaptor<ReminderDTO> reminderDTOArgumentCaptor;
    @Captor
    private ArgumentCaptor<ReminderTaskDTO> reminderTaskDTOArgumentCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createReminder() {
        ReminderDTO reminder = createReminderDTO();
        when(reminderService.create(any(ReminderDTO.class))).thenReturn(reminder);

        reminderRestController.createReminder(reminder);

        verify(reminderService).create(any(ReminderDTO.class));
        verify(schedulerClient).schedule(reminderTaskDTOArgumentCaptor.capture());
        verify(reminderService).update(reminderDTOArgumentCaptor.capture());

        ReminderDTO reminderDTO = reminderDTOArgumentCaptor.getValue();
        assertThat(reminderDTO.getRecipient()).isEqualTo(reminder.getRecipient());
        assertThat(reminderDTO.getText()).isEqualTo(reminder.getText());
        assertThat(reminderDTO.getCron()).isEqualTo(reminder.getCron());
        assertThat(reminderDTO.getStatus()).isEqualTo("SCHEDULED");
    }

    @Test
    public void getReminders() {
        reminderRestController.getReminders();

        verify(reminderService).getAll();
    }

    @Test
    public void getReminder() {
        String reminderId = UUID.randomUUID().toString();

        reminderRestController.getReminder(reminderId);

        verify(reminderService).read(eq(reminderId));
    }

    @Test
    public void updateReminder() {
        ReminderDTO reminder = createReminderDTO();
        reminder.setStatus("SCHEDULED");
        reminder.setId(UUID.randomUUID().toString());

        reminderRestController.updateReminder(reminder);

        verify(reminderService).update(reminderDTOArgumentCaptor.capture());
        ReminderDTO value = reminderDTOArgumentCaptor.getValue();
        assertThat(value.getRecipient()).isEqualTo(reminder.getRecipient());
        assertThat(value.getText()).isEqualTo(reminder.getText());
        assertThat(value.getCron()).isEqualTo(reminder.getCron());
        assertThat(value.getStatus()).isEqualTo(reminder.getStatus());
        assertThat(value.getId()).isEqualTo(reminder.getId());
    }

    //TODO Replace with ReminderDTO fixture
    private ReminderDTO createReminderDTO() {
        ReminderDTO reminder = new ReminderDTO();
        reminder.setRecipient("email@test.com");
        reminder.setText("Wake Up");
        reminder.setCron("0/40 * * * * ?");
        return reminder;
    }
}