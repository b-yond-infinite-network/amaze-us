package com.beyond.micorservice.driver.controller;

import lombok.RequiredArgsConstructor;
import com.beyond.micorservice.driver.entity.Driver;
import com.beyond.micorservice.driver.service.DriverService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/driver",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5000", allowedHeaders = "*")
public class DriverController {
    private final DriverService driverService;
    
    @PostMapping
    public Driver createDriver(@RequestBody final Driver driver) {
        return driverService.createDriver(driver);
    }
    
}
