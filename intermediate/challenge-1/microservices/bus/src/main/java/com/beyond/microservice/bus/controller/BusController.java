package com.beyond.microservice.bus.controller;

import com.beyond.microservice.bus.entity.Bus;
import com.beyond.microservice.bus.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/bus",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
public class BusController {
    private final BusService busService;
    
    @Autowired
    public BusController(BusService busService) {
        this.busService = busService;
    }
    
    @PostMapping
    public Bus createBus(@RequestBody final Bus bus) {
        return busService.createBus(bus);
    }
}
