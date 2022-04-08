package com.audela.challenge.busapi.service;

import com.audela.challenge.busapi.entity.BusEntity;
import com.audela.challenge.busapi.entity.DriverEntity;
import org.springframework.http.ResponseEntity;

public interface BusService {
    ResponseEntity createDriver(DriverEntity driver);

    ResponseEntity createBus(BusEntity bus);
}
