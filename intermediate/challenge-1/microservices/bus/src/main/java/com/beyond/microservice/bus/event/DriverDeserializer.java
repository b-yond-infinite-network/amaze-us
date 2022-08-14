package com.beyond.microservice.bus.event;

import com.beyond.microservice.bus.driver.Driver;
import org.springframework.kafka.support.serializer.JsonDeserializer;

public class DriverDeserializer extends JsonDeserializer<Driver> {
    public DriverDeserializer() {
        super(Driver.class);
    }
}
