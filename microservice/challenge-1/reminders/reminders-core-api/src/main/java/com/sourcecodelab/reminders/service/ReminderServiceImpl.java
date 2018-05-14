package com.sourcecodelab.reminders.service;

import com.sourcecodelab.reminders.model.entities.ReminderEntity;
import com.sourcecodelab.reminders.model.repositories.ReminderRepository;
import com.sourcecodelab.reminders.rest.dto.ReminderDTO;
import com.sourcecodelab.reminders.service.exceptions.ErrorCode;
import com.sourcecodelab.reminders.service.exceptions.ReminderServiceException;
import com.sourcecodelab.reminders.service.mapper.ServiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReminderServiceImpl implements ReminderService{
    private final ServiceMapper serviceMapper;
    private final ReminderRepository reminderRepository;

    @Autowired
    public ReminderServiceImpl (ServiceMapper serviceMapper, ReminderRepository reminderRepository) {
        this.serviceMapper = serviceMapper;
        this.reminderRepository = reminderRepository;
    }

    @Override
    public ReminderDTO create(ReminderDTO reminder) {
        ReminderEntity reminderEntity = reminderRepository.save(serviceMapper.map(reminder, ReminderEntity.class));
        return serviceMapper.map(reminderEntity, ReminderDTO.class);
    }

    @Override
    public ReminderDTO update(ReminderDTO reminderDTO) {
        ReminderEntity reminderEntity = reminderRepository.readByExternalId(reminderDTO.getId());
        reminderEntity.setCron(reminderDTO.getCron());
        reminderEntity.setText(reminderDTO.getText());
        reminderEntity.setRecipient(reminderDTO.getRecipient());
        return serviceMapper.map(reminderRepository.save(reminderEntity), ReminderDTO.class);
    }

    @Override
    public ReminderDTO read(String externalId) {
        return Optional.ofNullable(serviceMapper.map(reminderRepository.readByExternalId(externalId), ReminderDTO.class)).orElseThrow(() -> new ReminderServiceException(ErrorCode.RESOURCE_NOT_FOUND));
    }

    @Override
    public List<ReminderDTO> getAll() {
        return serviceMapper.mapAsList(reminderRepository.findAll(), ReminderDTO.class);
    }
}
