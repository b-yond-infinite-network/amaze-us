package com.beyond.microservice.schedule.events;

import com.beyond.microservice.schedule.driver.Driver;
import org.springframework.kafka.support.serializer.JsonDeserializer;

public class DriverDeserializaer extends JsonDeserializer<Driver> {
    public DriverDeserializaer() {
        super(Driver.class);
    }
}

