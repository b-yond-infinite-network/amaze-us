package com.beyond.microservice.bus.service;

import com.beyond.microservice.bus.driver.Driver;
import com.beyond.microservice.bus.driver.DriverRepository;
import com.beyond.microservice.bus.entity.Bus;
import com.beyond.microservice.bus.repository.BusRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;

@Service
@RequiredArgsConstructor
public class BusService {
    private final BusRepository busRepository;
    
    private final DriverRepository driverRepository;
    private final KafkaTemplate<String, Bus> kafkaTemplate;
    
    public Bus createBus(final Bus bus) {
        Bus newBus = busRepository.save(bus);
//        fireBusCreatedEvent(bus);
        return newBus;
    }
    
    private void fireBusCreatedEvent(final Bus bus) {
        kafkaTemplate.send("order", bus.getBusId() + "created", bus);
    }
    
    public void createDriver(final Driver driver) {
        driverRepository.save(driver);
    }
}
