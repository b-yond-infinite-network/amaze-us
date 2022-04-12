package com.audela.challenge.busapi.service.impl;

import com.audela.challenge.busapi.entity.BusEntity;
import com.audela.challenge.busapi.entity.DriverEntity;
import com.audela.challenge.busapi.entity.ScheduleEntity;
import com.audela.challenge.busapi.exception.DataValidationException;
import com.audela.challenge.busapi.exception.ScheduleConflictException;
import com.audela.challenge.busapi.repository.BusRepository;
import com.audela.challenge.busapi.repository.DriverRepository;
import com.audela.challenge.busapi.repository.ScheduleRepository;
import com.audela.challenge.busapi.service.BusService;
import com.audela.challenge.busapi.util.EmailUtil;
import com.audela.challenge.busapi.validation.ScheduleValidation;
import com.audela.challenge.busapi.vo.BusScheduleVo;
import com.audela.challenge.busapi.vo.DriverScheduleVo;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.security.RolesAllowed;
import java.lang.reflect.InvocationTargetException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BusServiceImpl implements BusService {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ScheduleValidation scheduleValidation;

    @Autowired
    private EmailUtil emailUtil;

    @Override
    @RolesAllowed({ "ROLE_MANAGER" })
    public ResponseEntity createDriver(DriverEntity driver) {
        DriverEntity driverResult = driverRepository.save(driver);
        return new ResponseEntity<>(driverResult, HttpStatus.CREATED);
    }

    @Override
    @RolesAllowed({ "ROLE_MANAGER" })
    public ResponseEntity createBus(BusEntity bus) {
        BusEntity busRes = busRepository.save(bus);
        return new ResponseEntity<>(busRes, HttpStatus.CREATED);
    }

    @Override
    @RolesAllowed({ "ROLE_MANAGER" })
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public ResponseEntity<ScheduleEntity> createSchedule(ScheduleEntity schedule) throws ScheduleConflictException, DataValidationException {
        scheduleValidation.validateForCreation(schedule);
        ScheduleEntity newSchedule = scheduleRepository.save(schedule);
        DriverEntity driver = driverRepository.findById(schedule.getDriver().getId()).get();
        emailUtil.sendEmail(driver.getEmail(),"Schedule created","New schedule created - "+schedule.toString());
        return new ResponseEntity<>(newSchedule, HttpStatus.CREATED);
    }

    @Override
    @RolesAllowed({ "ROLE_EMPLOYEE", "ROLE_MANAGER" })
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
    @RolesAllowed({ "ROLE_EMPLOYEE", "ROLE_MANAGER" })
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
    @RolesAllowed({ "ROLE_MANAGER" })
    @Transactional(rollbackFor = RuntimeException.class, propagation = Propagation.REQUIRES_NEW)
    public ResponseEntity<ScheduleEntity> updateSchedule(ScheduleEntity schedule) throws ScheduleConflictException, DataValidationException {
        scheduleValidation.validateForUpdate(schedule);
        int count = scheduleRepository.updateSchedule(schedule.getId(),
                schedule.getBus().getId(),
                schedule.getDriver().getId(),
                schedule.getStartStation(),
                schedule.getDestinationStation(),
                schedule.getEtd(),
                schedule.getEta(),
                schedule.getAtd(),
                schedule.getAta());
        DriverEntity driver = driverRepository.findById(schedule.getDriver().getId()).get();
        emailUtil.sendEmail(driver.getEmail(),"Schedule created","Schedule updated - "+schedule.toString());
        return new ResponseEntity<>(schedule, HttpStatus.OK);
    }

    @Override
    @RolesAllowed({ "ROLE_MANAGER" })
    public ResponseEntity<String> deleteSchedule(int id) {
        Optional<ScheduleEntity> result = scheduleRepository.findById(id);
        ScheduleEntity schedule = result.isPresent() ? result.get() : null;
        if(schedule == null){
            throw new DataValidationException("Schedule not available");
        }
        scheduleRepository.delete(schedule);
        DriverEntity driver = driverRepository.findById(schedule.getDriver().getId()).get();
        emailUtil.sendEmail(driver.getEmail(),"Schedule created","Schedule deleted - "+schedule.toString());
        return new ResponseEntity<>("Schedule deleted", HttpStatus.OK);
    }

    @Override
    @RolesAllowed({ "ROLE_MANAGER" })
    public ResponseEntity<DriverEntity> updateDriver(DriverEntity driver) {
        driverRepository.updateDriver(driver.getId(),
                driver.getFirstName(),
                driver.getLastName(),
                driver.getSsn(),
                driver.getEmail());
        return new ResponseEntity<>(driver, HttpStatus.OK);
    }

    @Override
    @RolesAllowed({ "ROLE_MANAGER" })
    public ResponseEntity<String> deleteDriver(int id) {
        driverRepository.deleteById(id);
        return new ResponseEntity<>("Driver deleted", HttpStatus.OK);
    }

    @Override
    @RolesAllowed({ "ROLE_MANAGER" })
    public ResponseEntity<BusEntity> updateBus(BusEntity bus) {
        busRepository.updateBus(bus.getId(),
                bus.getCapacity(),
                bus.getModel(),
                bus.getMake());
        return new ResponseEntity<>(bus, HttpStatus.OK);
    }

    @Override
    @RolesAllowed({ "ROLE_MANAGER" })
    public ResponseEntity<String> deleteBus(int id) {
        busRepository.deleteById(id);
        return new ResponseEntity<>("Bus deleted", HttpStatus.OK);
    }
}
