package com.beyond.microservice.driver.unit.service;

import static org.mockito.Mockito.times;

import com.beyond.microservice.driver.entity.Driver;
import com.beyond.microservice.driver.repository.DriverRepository;
import com.beyond.microservice.driver.service.DriverService;
import com.beyond.microservice.driver.util.DriverTestUtil;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DriverServiceTest {
    private DriverRepository driverRepository;
    private DriverService driverService;
    
    @BeforeMethod
    public void setup() {
        driverRepository    = Mockito.mock(DriverRepository.class);
        KafkaTemplate kafkaTemplate = Mockito.mock(KafkaTemplate.class);
        driverService = new DriverService(driverRepository, kafkaTemplate);
    }
    
    @Test
    public void insertDriver_should_insert_valid_Driver() {
        Driver driver = DriverTestUtil.getDriver();
        Mockito.when(driverRepository.save(driver)).thenReturn(driver);
        
        Driver actualDriver = driverService.createDriver(driver);
        
        Assert.assertNotNull(actualDriver, "Service should insert a Bus not null");
        Assert.assertNotNull(actualDriver, "Controller should insert the project");
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
        Mockito.verify(driverRepository, times(1)).save(driver);
        Mockito.verifyNoMoreInteractions(driverRepository);
    }
    
    @Test
    public void insertBusById_should_return_null() {
        Driver driver =  new Driver();
        Mockito.when(driverRepository.save(driver)).thenReturn(null);
        Driver actualDriver = driverService.createDriver(driver);
        Assert.assertNull(actualDriver, "Service expected to return null");
        Mockito.verify(driverRepository, times(1)).save(driver);
        Mockito.verifyNoMoreInteractions(driverRepository);
    }
}
