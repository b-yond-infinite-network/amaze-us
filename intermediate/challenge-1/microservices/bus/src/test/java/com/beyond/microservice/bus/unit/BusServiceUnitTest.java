package com.beyond.microservice.bus.unit;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;

import java.util.Optional;

import com.beyond.microservice.bus.driver.Driver;
import com.beyond.microservice.bus.driver.DriverRepository;
import com.beyond.microservice.bus.entity.Bus;
import com.beyond.microservice.bus.repository.BusRepository;
import com.beyond.microservice.bus.service.BusService;
import com.beyond.microservice.bus.util.BusTestUtil;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BusServiceUnitTest {
    private BusRepository busRepository;
    private BusService busService;
    
    private DriverRepository driverRepository;
    private KafkaTemplate kafkaTemplate;
    
    @BeforeMethod
    public void setup() {
        busRepository = Mockito.mock(BusRepository.class);
        driverRepository = Mockito.mock(DriverRepository.class);
        kafkaTemplate = Mockito.mock(KafkaTemplate.class);
        busService    = new BusService(busRepository, driverRepository, kafkaTemplate);
    }
    
    @Test
    public void insertBus_should_insert_valid_Bus() {
        Bus bus = BusTestUtil.getBus();
        Mockito.when(driverRepository.findById(anyLong())).thenReturn(Optional.of(new Driver()));
        Mockito.when(busRepository.save(bus)).thenReturn(bus);
        Bus actualBus = busService.createBus(bus);
        
        Assert.assertNotNull(actualBus, "Service should insert a Bus not null");
        Assert.assertNotNull(actualBus, "Controller should insert the project");
        Assert.assertEquals(actualBus.getCapacity(), bus.getCapacity(),
                            "Inserted bus capacity should be" + bus.getCapacity());
        Assert.assertEquals(actualBus.getMaker(), bus.getMaker(),
                            "Inserted bus make should be" + bus.getMaker());
        Assert.assertEquals(actualBus.getModel(), bus.getModel(),
                            "Inserted bus model should be" + bus.getModel());
        Assert.assertEquals(actualBus.getDriver().getId(), bus.getDriver().getId(),
                            "Inserted bus should have driver's Id  " + bus.getDriver().getId() +
                                " drivers");
        Mockito.verify(busRepository, times(1)).save(bus);
        Mockito.verifyNoMoreInteractions(busRepository);
    }
    
    @Test
    public void insertBusById_should_return_null() {
        Bus bus = BusTestUtil.getBus();
        Mockito.when(busRepository.save(bus)).thenReturn(null);
        Mockito.when(driverRepository.findById(bus.getDriver().getId())).thenReturn(
            Optional.of(new Driver()));
        Bus actualBus = busService.createBus(bus);
        Assert.assertNull(actualBus, "Service expected to return null");
        Mockito.verify(busRepository, times(1)).save(bus);
        Mockito.verifyNoMoreInteractions(busRepository);
    }
}
