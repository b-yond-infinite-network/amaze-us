package com.audela.challenge.busapi.controller;


import com.audela.challenge.busapi.entity.BusEntity;
import com.audela.challenge.busapi.entity.DriverEntity;
import com.audela.challenge.busapi.entity.ScheduleEntity;
import com.audela.challenge.busapi.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BusController {

    @Autowired
    private BusService busService;

    @PostMapping(value = "/driver", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DriverEntity> createDriver(@RequestBody DriverEntity driver){

        return busService.createDriver(driver);
    }

    @PostMapping(value = "/bus", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BusEntity> createBus(@RequestBody BusEntity bus){

        return busService.createBus(bus);
    }

    @PostMapping(value = "/schedule", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ScheduleEntity> createSchedule(@RequestBody ScheduleEntity schedule){

        return busService.createSchedule(schedule);
    }
}
