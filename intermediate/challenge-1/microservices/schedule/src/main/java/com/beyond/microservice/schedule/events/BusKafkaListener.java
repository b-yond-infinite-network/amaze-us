package com.beyond.microservice.schedule.events;

import com.beyond.microservice.schedule.bus.Bus;
import com.beyond.microservice.schedule.driver.Driver;
import com.beyond.microservice.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class BusKafkaListener {
    private ScheduleService scheduleService;
    
    @KafkaListener(topics = "bus")
    public void createBus(Bus bus, Acknowledgment acknowledgment) {
        log.info("Received bus " + bus.getId());
        scheduleService.createBus(bus);
        acknowledgment.acknowledge();
    }
    
    @KafkaListener(topics = "driver")
    public void createDriver(Driver driver, Acknowledgment acknowledgment) {
        log.info("Received driver " + driver.getId());
        scheduleService.createDriver(driver);
        acknowledgment.acknowledge();
    }
    
}
