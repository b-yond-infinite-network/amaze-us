package com.beyond.microservice.bus.service;

import com.beyond.microservice.bus.entity.Bus;
import com.beyond.microservice.bus.repository.BusRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;

@Service
public class BusService {
    private BusRepository busRepository;
    private KafkaTemplate<String, Bus> kafkaTemplate;
    
    @Autowired
    public BusService(BusRepository busRepository, KafkaTemplate<String, Bus> kafkaTemplate) {
        super();
        this.busRepository = busRepository;
        this.kafkaTemplate = kafkaTemplate;
    }
    
    public Bus createBus(final Bus bus) {
        Bus newBus = busRepository.save(bus);
        fireBusCreatedEvent(bus);
        return newBus;
        
    }
    
    private void fireBusCreatedEvent(final Bus bus) {
        kafkaTemplate.send("order", bus.getBusId() + "created", bus);
    }
    
}
