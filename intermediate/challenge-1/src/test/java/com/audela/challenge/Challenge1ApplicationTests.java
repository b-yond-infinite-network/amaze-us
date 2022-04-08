package com.audela.challenge;

import com.audela.challenge.busapi.entity.BusEntity;
import com.audela.challenge.busapi.entity.DriverEntity;
import org.junit.Assert;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Challenge1ApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	int randomServerPort;

	@Test
	@Order(1)
	void testCreateDriver() throws URISyntaxException {
		URI uri = new URI("http://localhost:"+randomServerPort+"/bus-app/api/driver");
		DriverEntity driver = new DriverEntity();
		driver.setEmail("email1@domain.com");
		driver.setFirstName("First1");
		driver.setLastName("Last1");
		driver.setSsn("ssn1");
		HttpEntity<DriverEntity> request = new HttpEntity<>(driver);
		ResponseEntity<DriverEntity> response = restTemplate.postForEntity(uri,request, DriverEntity.class);
		Assert.assertTrue(response.getStatusCode() == HttpStatus.CREATED);
		Assert.assertEquals("email1@domain.com" , response.getBody().getEmail());
	}

	@Test
	@Order(2)
	void testCreateBus() throws URISyntaxException {
		URI uri = new URI("http://localhost:"+randomServerPort+"/bus-app/api/bus");
		BusEntity bus = new BusEntity();
		bus.setCapacity(50);
		bus.setMake("make1");
		bus.setModel("model1");
		HttpEntity<BusEntity> request = new HttpEntity<>(bus);
		ResponseEntity<BusEntity> response = restTemplate.postForEntity(uri,request, BusEntity.class);
		Assert.assertTrue(response.getStatusCode() == HttpStatus.CREATED);
		Assert.assertEquals("model1" , response.getBody().getModel());
	}

}
