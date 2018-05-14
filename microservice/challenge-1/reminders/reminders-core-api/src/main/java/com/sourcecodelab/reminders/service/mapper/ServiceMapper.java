package com.sourcecodelab.reminders.service.mapper;

import com.sourcecodelab.reminders.model.entities.ReminderEntity;
import com.sourcecodelab.reminders.rest.dto.ReminderDTO;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;

public class ServiceMapper extends ConfigurableMapper {
    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(ReminderDTO.class, ReminderEntity.class)
                .byDefault()
                .field("id", "externalId")
                .register();
    }
}
