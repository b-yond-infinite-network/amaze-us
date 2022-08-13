package com.beyond.microservice.bus.controller;

import com.beyond.microservice.bus.entity.Bus;
import com.beyond.microservice.bus.repository.BusRepository;
import com.beyond.microservice.bus.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class BusController {
    private BusService busService;
    
    @Autowired
    public BusController(BusService invoiceRepository) {
        this.busService = invoiceRepository;
    }
    
    @PostMapping
    public Bus CreateBus(@RequestBody final Bus bus) {
        return busService.createBus(bus);
    }
}
