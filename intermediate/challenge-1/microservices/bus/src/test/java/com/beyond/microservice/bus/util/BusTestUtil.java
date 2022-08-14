package com.beyond.microservice.bus.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.beyond.microservice.bus.driver.Driver;
import com.beyond.microservice.bus.entity.Bus;

public class BusTestUtil {
    
    private static final int driverNo = 4;
    
    public static Driver getDriver() {
        return Driver.builder()
                     .firstName("firstName" + System.currentTimeMillis())
                     .name("name" + System.currentTimeMillis())
                     .build();
        
    }
    
    public static Bus getBus() {
        List<Driver> drivers = IntStream.range(0, driverNo).mapToObj(i -> getDriver()).collect(
            Collectors.toList());
        return Bus.builder()
            .maker("toyo" +  System.currentTimeMillis())
            .model("AV" + System.currentTimeMillis())
            .capacity(1000)
            .driver(drivers)
            .build();
    }
    
    
}
