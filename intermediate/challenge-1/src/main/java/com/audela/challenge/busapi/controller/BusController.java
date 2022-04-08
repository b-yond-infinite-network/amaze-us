package com.audela.challenge.busapi.controller;


import com.audela.challenge.busapi.entity.BusEntity;
import com.audela.challenge.busapi.entity.DriverEntity;
import com.audela.challenge.busapi.entity.ScheduleEntity;
import com.audela.challenge.busapi.service.BusService;
import com.audela.challenge.busapi.vo.BusScheduleVo;
import com.audela.challenge.busapi.vo.DriverScheduleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping(value = "/driver_schedule/{driver_id}/{yyyymmdd}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<DriverScheduleVo>> getDriverSchedule(@PathVariable int driver_id, @PathVariable  String yyyymmdd){
        return busService.getDriverSchedule(driver_id, yyyymmdd);
    }

    @GetMapping(value = "/bus_schedule/{bus_id}/{yyyymmdd}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BusScheduleVo>> getBusSchedule(@PathVariable int bus_id, @PathVariable  String yyyymmdd){
        return busService.getBusSchedule(bus_id, yyyymmdd);
    }

    @PutMapping(value = "/schedule", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ScheduleEntity> updateSchedule(@RequestBody ScheduleEntity schedule){
        return busService.updateSchedule(schedule);
    }

    @DeleteMapping(value = "/schedule/{id}")
    public ResponseEntity<String> deleteSchedule(@PathVariable int id){
        return busService.deleteSchedule(id);
    }
}
