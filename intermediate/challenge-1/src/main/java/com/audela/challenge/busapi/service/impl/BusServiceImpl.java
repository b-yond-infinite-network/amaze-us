package com.audela.challenge.busapi.service.impl;

import com.audela.challenge.busapi.entity.BusEntity;
import com.audela.challenge.busapi.entity.DriverEntity;
import com.audela.challenge.busapi.repository.BusRepository;
import com.audela.challenge.busapi.repository.DriverRepository;
import com.audela.challenge.busapi.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BusServiceImpl implements BusService {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private BusRepository busRepository;

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
}
