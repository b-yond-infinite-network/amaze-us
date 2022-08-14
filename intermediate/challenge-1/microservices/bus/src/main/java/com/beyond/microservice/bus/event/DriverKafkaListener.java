package com.beyond.microservice.bus.event;

import com.beyond.microservice.bus.driver.Driver;
import com.beyond.microservice.bus.service.BusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class DriverKafkaListener {
    private BusService busService;
    
    @KafkaListener(topics = "driver")
    public void createDriver(Driver driver, Acknowledgment acknowledgment) {
        log.info("Received invoice " + driver.getId());
        busService.createDriver(driver);
        acknowledgment.acknowledge();
    }
    
}
