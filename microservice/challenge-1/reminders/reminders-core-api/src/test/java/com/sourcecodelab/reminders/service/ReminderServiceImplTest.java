package com.sourcecodelab.reminders.service;

import com.sourcecodelab.reminders.model.entities.ReminderEntity;
import com.sourcecodelab.reminders.model.repositories.ReminderRepository;
import com.sourcecodelab.reminders.rest.dto.ReminderDTO;
import com.sourcecodelab.reminders.service.exceptions.ReminderServiceException;
import com.sourcecodelab.reminders.service.mapper.ServiceMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.UUID;

import static com.sourcecodelab.reminders.service.exceptions.ErrorCode.RESOURCE_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ReminderServiceImplTest {
    @Mock
    private ServiceMapper serviceMapper;
    @Mock
    private ReminderRepository reminderRepository;
    @InjectMocks
    private ReminderServiceImpl reminderService;

    @Captor
    private ArgumentCaptor<ReminderEntity> reminderEntityArgumentCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void create() {
        ReminderDTO reminderDTO = createReminderDTO();
        when(serviceMapper.map(eq(reminderDTO), eq(ReminderEntity.class))).thenReturn(new ReminderEntity());
        when(reminderRepository.save(any(ReminderEntity.class))).thenReturn(new ReminderEntity());

        reminderService.create(reminderDTO);

        verify(serviceMapper).map(eq(reminderDTO), eq(ReminderEntity.class));
        verify(serviceMapper).map(any(ReminderEntity.class), eq(ReminderDTO.class));
    }

    @Test
    public void update() {
        ReminderDTO reminderDTO = createReminderDTO();
        when(reminderRepository.readByExternalId(eq(reminderDTO.getId()))).thenReturn(new ReminderEntity());
        when(reminderRepository.save(any(ReminderEntity.class))).thenReturn(new ReminderEntity());

        reminderService.update(reminderDTO);

        verify(reminderRepository).readByExternalId(eq(reminderDTO.getId()));
        verify(reminderRepository).save(reminderEntityArgumentCaptor.capture());
        ReminderEntity value = reminderEntityArgumentCaptor.getValue();
        assertThat(reminderDTO.getCron()).isEqualTo(value.getCron());
        assertThat(reminderDTO.getText()).isEqualTo(value.getText());
        assertThat(reminderDTO.getRecipient()).isEqualTo(value.getRecipient());
        verify(serviceMapper).map(any(ReminderEntity.class), eq(ReminderDTO.class));
    }

    @Test
    public void read() {
        String reminderId = UUID.randomUUID().toString();
        when(reminderRepository.readByExternalId(eq(reminderId))).thenReturn(new ReminderEntity());
        when(serviceMapper.map(any(ReminderEntity.class), eq(ReminderDTO.class))).thenReturn(new ReminderDTO());

        reminderService.read(reminderId);

        verify(reminderRepository).readByExternalId(eq(reminderId));
    }

    @Test
    public void read_notHavingTheReminder_shouldThrowAnException() {
        String reminderId = UUID.randomUUID().toString();

        Throwable thrown = catchThrowable(() -> reminderService.read(reminderId));

        assertThat(thrown).isInstanceOf(ReminderServiceException.class);
        ReminderServiceException reminderServiceException = (ReminderServiceException) thrown;
        assertThat(reminderServiceException.getCode()).isEqualTo(RESOURCE_NOT_FOUND.getCode());
        assertThat(reminderServiceException.getMessage()).isEqualTo(RESOURCE_NOT_FOUND.getMessage());
    }

    @Test
    public void getAll() {
        reminderService.getAll();

        verify(reminderRepository).findAll();
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