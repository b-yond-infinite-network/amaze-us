package com.beyond.microservice.bus.e2e;

import java.net.URI;
import java.net.URISyntaxException;

import com.beyond.microservice.bus.entity.Bus;
import com.beyond.microservice.bus.repository.BusRepository;
import com.beyond.microservice.bus.util.BusTestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes =
    com.beyond.microservice.bus.BusApplication.class)
@DirtiesContext
class BusControllerE2ETest {
    
    @Autowired
    protected TestRestTemplate testRestTemplate;
    
    @LocalServerPort
    private int port;
    
    @Autowired
    BusControllerE2ETest(final BusRepository busRepository) {
    }
    
    @Test
    void Insert_Bus_Node() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + port + "/bus";
        URI uri = new URI(baseUrl);
        Bus bus = BusTestUtil.getBus();
     
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<Bus> request = new HttpEntity<>(bus, headers);
        ResponseEntity<Bus> result = this.testRestTemplate.postForEntity(uri, request, Bus.class);
        Assertions.assertEquals(200, result.getStatusCodeValue());
    }
    
    }
    

    
