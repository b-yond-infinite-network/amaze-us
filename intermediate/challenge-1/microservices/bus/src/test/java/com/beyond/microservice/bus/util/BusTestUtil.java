package com.beyond.microservice.bus.util;

import com.beyond.microservice.bus.driver.Driver;
import com.beyond.microservice.bus.entity.Bus;

public class BusTestUtil {
    
    private static final int capacity = 1000;
    
    public static Driver getDriver() {
        return Driver.builder()
                     .id(System.currentTimeMillis())
                     .build();
        
    }
    
    public static Bus getBus() {
        return Bus.builder()
            .id(System.currentTimeMillis())
            .maker("toyo" +  System.currentTimeMillis())
            .model("AV" + System.currentTimeMillis())
            .capacity(1000)
            .driver(getDriver())
            .build();
    }
    
    
}
