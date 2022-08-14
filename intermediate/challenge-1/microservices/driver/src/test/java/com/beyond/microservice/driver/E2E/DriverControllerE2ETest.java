package com.beyond.microservice.driver.E2E;

import java.net.URI;
import java.net.URISyntaxException;

import com.beyond.microservice.driver.entity.Driver;
import com.beyond.microservice.driver.repository.DriverRepository;
import com.beyond.microservice.driver.util.DriverTestUtil;
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
    com.beyond.microservice.driver.DriverApplication.class)
@DirtiesContext
class DriverControllerE2ETest {
    @Autowired
    protected TestRestTemplate testRestTemplate;
    @LocalServerPort
    private int port;
    
    @Autowired
    DriverControllerE2ETest(final DriverRepository driverRepository) {
    }
    
    @Test
    void Insert_Bus_Node() throws URISyntaxException {
        final String baseUrl = "http://localhost:" + port + "/bus";
        URI uri = new URI(baseUrl);
        Driver driver = DriverTestUtil.getDriver();
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<Driver> request = new HttpEntity<>(driver, headers);
        ResponseEntity<Driver> result = this.testRestTemplate.postForEntity(uri, request,
                                                                            Driver.class);
        Assertions.assertEquals(200, result.getStatusCodeValue());
    }
}
