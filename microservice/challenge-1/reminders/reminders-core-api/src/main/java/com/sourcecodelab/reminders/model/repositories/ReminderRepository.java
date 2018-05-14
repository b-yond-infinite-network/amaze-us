package com.sourcecodelab.reminders.model.repositories;

import com.sourcecodelab.reminders.model.entities.ReminderEntity;
import org.springframework.data.repository.CrudRepository;

public interface ReminderRepository extends CrudRepository<ReminderEntity, Long> {
    ReminderEntity readByExternalId(String externalId);
}
