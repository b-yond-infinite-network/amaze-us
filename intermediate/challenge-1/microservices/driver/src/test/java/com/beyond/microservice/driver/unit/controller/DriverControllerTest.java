package com.beyond.microservice.driver.unit.controller;

import static org.mockito.Mockito.times;

import com.beyond.microservice.driver.controller.DriverController;
import com.beyond.microservice.driver.entity.Driver;
import com.beyond.microservice.driver.service.DriverService;
import com.beyond.microservice.driver.util.DriverTestUtil;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DriverControllerTest {
    
    private DriverService driverService;
    private DriverController driverController;
    
    @BeforeMethod
    public void setup() {
        driverService    = Mockito.mock(DriverService.class);
        driverController = new DriverController(driverService);
    }
    
    @Test
    public void insertProjectById_should_insert_valid_project() {
        Driver driver = DriverTestUtil.getDriver();
        
        Mockito.when(driverService.createDriver(driver)).thenReturn(driver);
        
        Driver actualDriver = driverController.createDriver(driver);
        
        Assert.assertNotNull(actualDriver, "Controller should insert the driver");
        Assert.assertEquals(actualDriver.getEmail(), driver.getEmail(),
                            "Inserted driver email should be" + driver.getEmail());
        Assert.assertEquals(actualDriver.getName(), driver.getName(),
                            "Inserted driver name should be" + driver.getName());
        Assert.assertEquals(actualDriver.getFirstName(), driver.getFirstName(),
                            "Inserted driver first name should be" + driver.getFirstName());
        Assert.assertEquals(actualDriver.getSocicalNum(), driver.getSocicalNum(),
                            "Inserted driver social insurance number should be "
                                + driver.getSocicalNum());
        
        Mockito.verify(driverService, times(1)).createDriver(driver);
        Mockito.verifyNoMoreInteractions(driverService);
    }
}
