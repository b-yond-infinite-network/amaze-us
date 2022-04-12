package com.audela.challenge;

import com.audela.challenge.busapi.entity.BusEntity;
import com.audela.challenge.busapi.entity.DriverEntity;
import com.audela.challenge.busapi.entity.ScheduleEntity;
import com.audela.challenge.busapi.util.JwtUtils;
import com.audela.challenge.busapi.vo.BusScheduleVo;
import com.audela.challenge.busapi.vo.DriverScheduleVo;
import org.junit.Assert;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Challenge1ApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	int randomServerPort;

	@Autowired
	JwtUtils jwtUtils;

	private HttpHeaders getManagerTokenHeader(){
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type","application/json");
		Map<String,Object> payload = new HashMap<>();
		payload.put("name","Manager1");
		payload.put("role","ROLE_MANAGER");
		headers.set("Authorization","Bearer "+ jwtUtils.createJWT(payload));
		return headers;
	}

	@Test
	@Order(1)
	void testCreateDriver() throws URISyntaxException {
		URI uri = new URI("http://localhost:"+randomServerPort+"/bus-app/api/driver");
		DriverEntity driver = new DriverEntity();
		driver.setEmail("email1@domain.com");
		driver.setFirstName("First1");
		driver.setLastName("Last1");
		driver.setSsn("ssn1");
		HttpEntity<DriverEntity> request = new HttpEntity<>(driver,getManagerTokenHeader());
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
		HttpEntity<BusEntity> request = new HttpEntity<>(bus,getManagerTokenHeader());
		ResponseEntity<BusEntity> response = restTemplate.postForEntity(uri,request, BusEntity.class);
		Assert.assertTrue(response.getStatusCode() == HttpStatus.CREATED);
		Assert.assertEquals("model1" , response.getBody().getModel());
	}

	@Test
	@Order(3)
	void testCreateSchedule() throws URISyntaxException {
		URI uri = new URI("http://localhost:"+randomServerPort+"/bus-app/api/schedule");
		ScheduleEntity schedule = new ScheduleEntity();
		schedule.setEtd(OffsetDateTime.of(LocalDateTime.of(2022,4,8,10,00), ZoneOffset.UTC));
		schedule.setEta(OffsetDateTime.of(LocalDateTime.of(2022,4,8,10,30), ZoneOffset.UTC));
		schedule.setStartStation("Station A");
		schedule.setDestinationStation("Station B");
		BusEntity bus = new BusEntity();
		bus.setId(1);
		schedule.setBus(bus);
		DriverEntity driver = new DriverEntity();
		driver.setId(1);
		schedule.setDriver(driver);
		HttpEntity<ScheduleEntity> request = new HttpEntity<>(schedule,getManagerTokenHeader());
		ResponseEntity<ScheduleEntity> response = restTemplate.postForEntity(uri,request, ScheduleEntity.class);
		Assert.assertTrue(response.getStatusCode() == HttpStatus.CREATED);
		Assert.assertEquals("Station B" , response.getBody().getDestinationStation());
	}

	@Test
	@Order(4)
	void testGetDriverSchedule() throws URISyntaxException {
		String url = "http://localhost:"+randomServerPort+"/bus-app/api/driver_schedule/1/20220408";

		HttpEntity request = new HttpEntity<>(getManagerTokenHeader());
		ResponseEntity<List<DriverScheduleVo>> response = restTemplate.exchange(url, HttpMethod.GET,
				request, new ParameterizedTypeReference<List<DriverScheduleVo>>() {});
		Assert.assertTrue(response.getStatusCode() == HttpStatus.OK);
		Assert.assertTrue(response.getBody().size()>0);
	}

	@Test
	@Order(5)
	void testGetBusSchedule() throws URISyntaxException {
		String url = "http://localhost:"+randomServerPort+"/bus-app/api/bus_schedule/1/20220408";

		HttpEntity request = new HttpEntity<>(getManagerTokenHeader());
		ResponseEntity<List<BusScheduleVo>> response = restTemplate.exchange(url, HttpMethod.GET,
				request, new ParameterizedTypeReference<List<BusScheduleVo>>() {});
		Assert.assertTrue(response.getStatusCode() == HttpStatus.OK);
	}

}
