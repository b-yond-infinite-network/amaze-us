package com.beyond.microservice.bus.unit.controller;

import static org.mockito.Mockito.times;

import com.beyond.microservice.bus.controller.BusController;
import com.beyond.microservice.bus.entity.Bus;
import com.beyond.microservice.bus.service.BusService;
import com.beyond.microservice.bus.util.BusTestUtil;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class BusControllerUnitTest {
    
    private BusService busService;
    private BusController busController;
    
    @BeforeMethod
    public void setup() {
        busService    = Mockito.mock(BusService.class);
        busController = new BusController(busService);
    }
    
    
    @Test
    public void insertProjectById_should_insert_valid_project() {
        Bus bus = BusTestUtil.getBus();
        
        Mockito.when(busService.createBus(bus)).thenReturn(bus);
        
        Bus actualBus = busController.createBus(bus);
        
        Assert.assertNotNull(actualBus, "Controller should insert the project");
        Assert.assertEquals(actualBus.getCapacity(), bus.getCapacity(),
                            "Inserted bus capacity should be" + bus.getCapacity());
        Assert.assertEquals(actualBus.getMaker(), bus.getMaker(),
                            "Inserted bus make should be" + bus.getMaker());
        Assert.assertEquals(actualBus.getModel(), bus.getModel(),
                            "Inserted bus model should be" + bus.getModel());
        Assert.assertEquals(actualBus.getDriver().getId(), bus.getDriver().getId(),
                            "Inserted bus should have driver's first name  " + bus.getDriver().getId() +
                                " drivers");
        
        Mockito.verify(busService, times(1)).createBus(bus);
        Mockito.verifyNoMoreInteractions(busService);
    }
    
}
