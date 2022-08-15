package com.beyond.microservice.bus.service;

import com.beyond.microservice.bus.driver.Driver;
import com.beyond.microservice.bus.driver.DriverRepository;
import com.beyond.microservice.bus.entity.Bus;
import com.beyond.microservice.bus.repository.BusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BusService {
    private static final String topic = "bus";
    private final BusRepository busRepository;
    
    private final DriverRepository driverRepository;
    private final KafkaTemplate<String, Bus> kafkaTemplate;
    
    public Bus createBus(final Bus bus) {
        Driver driver = driverRepository.findById(bus.getDriver().getId()).orElse(null);
        if (driver == null) {
            log.error("driver does not exists");
            throw new NullPointerException("driver not found.");
        }
        
        Bus newBus = busRepository.save(bus);
        fireBusCreatedEvent(bus);
        return newBus;
    }
    
    private void fireBusCreatedEvent(final Bus bus) {
        kafkaTemplate.send(topic, bus.getId() + "created", bus);
    }
    
    public void createDriver(final Driver driver) {
        driverRepository.save(driver);
    }
}
