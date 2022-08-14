package com.beyond.microservice.driver.service;

import com.beyond.microservice.driver.entity.Driver;
import lombok.RequiredArgsConstructor;
import com.beyond.microservice.driver.repository.DriverRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverService {
    private final DriverRepository driverRepository;
    private final KafkaTemplate<String, Driver> kafkaTemplate;
    
    public Driver createDriver(Driver driver) {
        Driver result = driverRepository.save(driver);
        fireOrderCreatedEvent(driver);
        return result;
    }
    
    private void fireOrderCreatedEvent(Driver driver) {
        kafkaTemplate.send("driver", driver.getId() + "created", driver);
    }
}
