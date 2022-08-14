package com.beyond.microservice.bus.it;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.beyond.microservice.bus.BusApplication;
import com.beyond.microservice.bus.controller.BusController;
import com.beyond.microservice.bus.entity.Bus;
import com.beyond.microservice.bus.repository.BusRepository;
import com.beyond.microservice.bus.service.BusService;
import com.beyond.microservice.bus.util.BusTestUtil;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testng.annotations.Test;

@ContextConfiguration(classes = BusApplication.class)
@WebMvcTest(BusController.class)
public class BusControllerIntTest {
    @Autowired
    private BusController busController;
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private BusService busService;
    
    @MockBean
    private BusRepository busRepository;
    
    @Test(expectedExceptions = { Exception.class })
    public void Insert_Project_By_Id() throws Exception {
        
        Bus bus = BusTestUtil.getBus();
        Mockito.when(busService.createBus(bus)).thenReturn(bus);
        mockMvc.perform(MockMvcRequestBuilders.post("/projects/{id1}")
                                              .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().contentType("application/json"))
               .andExpect(jsonPath("$", hasSize(1)))
               .andExpect(jsonPath("$[1].make", containsString("toyo")));
    }
    
}
