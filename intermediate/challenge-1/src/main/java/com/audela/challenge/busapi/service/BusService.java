package com.audela.challenge.busapi.service;

import com.audela.challenge.busapi.entity.BusEntity;
import com.audela.challenge.busapi.entity.DriverEntity;
import com.audela.challenge.busapi.entity.ScheduleEntity;
import org.springframework.http.ResponseEntity;

public interface BusService {
    ResponseEntity createDriver(DriverEntity driver);

    ResponseEntity createBus(BusEntity bus);

    ResponseEntity<ScheduleEntity> createSchedule(ScheduleEntity schedule);
}
