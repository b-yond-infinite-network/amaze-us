package com.beyond.microservice.schedule.events;

import com.beyond.microservice.schedule.entity.Schedule;
import org.springframework.kafka.support.serializer.JsonDeserializer;

public class ScheduleDeserializer extends JsonDeserializer<Schedule> {
    public ScheduleDeserializer() {
        super(Schedule.class);
    }
}
