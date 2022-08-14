package com.beyond.microservice.driver.util;

import com.beyond.microservice.driver.entity.Driver;

public class DriverTestUtil {
    
    public static Driver getDriver() {
        return Driver.builder()
                     .email("mandy.wan@gmail.com")
                     .firstName("mandy" + System.currentTimeMillis())
                     .name("won" + System.currentTimeMillis())
                     .socicalNum("444-4444" + System.currentTimeMillis())
                     .build();
    }
}
