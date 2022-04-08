package com.audela.challenge.busapi.service.impl;

import com.audela.challenge.busapi.entity.BusEntity;
import com.audela.challenge.busapi.entity.DriverEntity;
import com.audela.challenge.busapi.entity.ScheduleEntity;
import com.audela.challenge.busapi.repository.BusRepository;
import com.audela.challenge.busapi.repository.DriverRepository;
import com.audela.challenge.busapi.repository.ScheduleRepository;
import com.audela.challenge.busapi.service.BusService;
import com.audela.challenge.busapi.vo.DriverScheduleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class BusServiceImpl implements BusService {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public ResponseEntity createDriver(DriverEntity driver) {
        DriverEntity driverResult = driverRepository.save(driver);
        return new ResponseEntity<>(driverResult, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity createBus(BusEntity bus) {
        BusEntity busRes = busRepository.save(bus);
        return new ResponseEntity<>(busRes, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ScheduleEntity> createSchedule(ScheduleEntity schedule) {
        ScheduleEntity newSchedule = scheduleRepository.save(schedule);
        return new ResponseEntity<>(newSchedule, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<DriverScheduleVo>> getDriverSchedule(int driverId, String yyyymmdd) {
        LocalDate date = LocalDate.parse(yyyymmdd, DateTimeFormatter.ofPattern("yyyyMMdd"));
        //LocalDate sunday = date.with(DayOfWeek.SUNDAY);
        LocalDate sunday = date.with(WeekFields.of(Locale.CANADA).dayOfWeek(), 1L);
        LocalDate saturday = date.with(WeekFields.of(Locale.CANADA).dayOfWeek(), 7L);
        ZoneId zone = ZoneId.of("America/Toronto");
        OffsetDateTime fromDate = sunday.atStartOfDay(zone).toOffsetDateTime();
        OffsetDateTime toDate = saturday.atStartOfDay(zone).toOffsetDateTime();
        List<ScheduleEntity> list = scheduleRepository.getScheduleByDriverIdWithinDateRange(driverId, fromDate, toDate);
        List<DriverScheduleVo>  scheduleVos = list.stream().map(x->{
            DriverScheduleVo vo = new DriverScheduleVo();
            vo.setBusId(x.getBus().getId());
            vo.setDriverId(x.getDriver().getId());
            vo.setScheduleId(x.getId());
            vo.setFirstName(x.getDriver().getFirstName());
            return vo;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(scheduleVos, HttpStatus.OK);
    }
}
