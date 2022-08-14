package com.beyond.microservice.schedule.bus;

import javax.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BusTestDataGenerator {
    private final BusRepository busRepository;
    
    @PostConstruct
    public void generateTestData() {
        busRepository.save(new Bus());
    }
    
}
