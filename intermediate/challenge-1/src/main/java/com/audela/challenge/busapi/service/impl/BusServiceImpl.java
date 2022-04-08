package com.audela.challenge.busapi.service.impl;

import com.audela.challenge.busapi.entity.BusEntity;
import com.audela.challenge.busapi.entity.DriverEntity;
import com.audela.challenge.busapi.entity.ScheduleEntity;
import com.audela.challenge.busapi.repository.BusRepository;
import com.audela.challenge.busapi.repository.DriverRepository;
import com.audela.challenge.busapi.repository.ScheduleRepository;
import com.audela.challenge.busapi.service.BusService;
import com.audela.challenge.busapi.vo.BusScheduleVo;
import com.audela.challenge.busapi.vo.DriverScheduleVo;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
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
            try {
                BeanUtils.copyProperties(vo,x);
                BeanUtils.copyProperties(vo,x.getDriver());
                BeanUtils.copyProperties(vo,x.getBus());
            } catch (Exception e) {
                e.printStackTrace();
            }
            vo.setBusId(x.getBus().getId());
            vo.setDriverId(x.getDriver().getId());
            vo.setScheduleId(x.getId());
            return vo;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(scheduleVos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<BusScheduleVo>> getBusSchedule(int busId, String yyyymmdd) {
        LocalDate date = LocalDate.parse(yyyymmdd, DateTimeFormatter.ofPattern("yyyyMMdd"));
        //LocalDate sunday = date.with(DayOfWeek.SUNDAY);
        LocalDate sunday = date.with(WeekFields.of(Locale.CANADA).dayOfWeek(), 1L);
        LocalDate saturday = date.with(WeekFields.of(Locale.CANADA).dayOfWeek(), 7L);
        ZoneId zone = ZoneId.of("America/Toronto");
        OffsetDateTime fromDate = sunday.atStartOfDay(zone).toOffsetDateTime();
        OffsetDateTime toDate = saturday.atStartOfDay(zone).toOffsetDateTime();
        List<ScheduleEntity> list = scheduleRepository.getScheduleByBusIdWithinDateRange(busId, fromDate, toDate);
        List<BusScheduleVo>  scheduleVos = list.stream().map(x->{
            BusScheduleVo vo = new BusScheduleVo();
            try {
                BeanUtils.copyProperties(vo,x);
                BeanUtils.copyProperties(vo,x.getDriver());
                BeanUtils.copyProperties(vo,x.getBus());
            } catch (Exception e) {
                e.printStackTrace();
            }
            vo.setBusId(x.getBus().getId());
            vo.setDriverId(x.getDriver().getId());
            vo.setScheduleId(x.getId());
            return vo;
        }).collect(Collectors.toList());
        return new ResponseEntity<>(scheduleVos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ScheduleEntity> updateSchedule(ScheduleEntity schedule) {
        int count = scheduleRepository.updateSchedule(schedule.getId(),
                schedule.getBus().getId(),
                schedule.getDriver().getId(),
                schedule.getStartStation(),
                schedule.getDestinationStation(),
                schedule.getEtd(),
                schedule.getEta(),
                schedule.getAtd(),
                schedule.getAta());
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteSchedule(int id) {
        ScheduleEntity schedule = new ScheduleEntity();
        schedule.setId(id);
        scheduleRepository.delete(schedule);
        return new ResponseEntity<>("Schedule deleted", HttpStatus.OK);
    }
}
