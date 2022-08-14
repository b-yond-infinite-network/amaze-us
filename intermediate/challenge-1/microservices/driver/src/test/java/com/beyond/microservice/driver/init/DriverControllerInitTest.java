package com.beyond.microservice.driver.init;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.beyond.microservice.driver.DriverApplication;
import com.beyond.microservice.driver.controller.DriverController;
import com.beyond.microservice.driver.entity.Driver;
import com.beyond.microservice.driver.repository.DriverRepository;
import com.beyond.microservice.driver.service.DriverService;
import com.beyond.microservice.driver.util.DriverTestUtil;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testng.annotations.Test;

@ContextConfiguration(classes = DriverApplication.class)
@WebMvcTest(DriverController.class)
public class DriverControllerInitTest {
    @Autowired
    private DriverController driverController;
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private DriverService driverService;
    
    @MockBean
    private DriverRepository busRepodriverRepositoryitory;
    
    @Test(expectedExceptions = { Exception.class })
    public void Insert_Project_By_Id() throws Exception {
        
        Driver driver = DriverTestUtil.getDriver();
        Mockito.when(driverService.createDriver(driver)).thenReturn(driver);
        mockMvc.perform(MockMvcRequestBuilders.post("/projects/{id1}")
                                              .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().contentType("application/json"))
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$[1].name", containsString("won")));
    }
    
}
