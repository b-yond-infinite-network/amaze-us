package com.audela.challenge.busapi.service;

import com.audela.challenge.busapi.entity.BusEntity;
import com.audela.challenge.busapi.entity.DriverEntity;
import com.audela.challenge.busapi.entity.ScheduleEntity;
import com.audela.challenge.busapi.vo.BusScheduleVo;
import com.audela.challenge.busapi.vo.DriverScheduleVo;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BusService {
    ResponseEntity createDriver(DriverEntity driver);

    ResponseEntity createBus(BusEntity bus);

    ResponseEntity<ScheduleEntity> createSchedule(ScheduleEntity schedule);

    ResponseEntity<List<DriverScheduleVo>> getDriverSchedule(int driver_id, String yyyymmdd);

    ResponseEntity<List<BusScheduleVo>> getBusSchedule(int bus_id, String yyyymmdd);

    ResponseEntity<ScheduleEntity> updateSchedule(ScheduleEntity schedule);

    ResponseEntity<String> deleteSchedule(int id);
}
