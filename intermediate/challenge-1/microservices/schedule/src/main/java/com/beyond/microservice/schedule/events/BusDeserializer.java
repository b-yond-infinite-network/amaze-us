package com.beyond.microservice.schedule.events;

import com.beyond.microservice.schedule.bus.Bus;
import org.springframework.kafka.support.serializer.JsonDeserializer;

public class BusDeserializer extends JsonDeserializer<Bus> {
    public BusDeserializer() {
        super(Bus.class);
    }
}
