package com.challenge.frontend.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.challenge.frontend.models.UserModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * This service connects to the webservice endpoint to
 * deal with Users
 */
@Service
public class UserService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private RestTemplate restTemplate = new RestTemplate();
	
	
	@Value("${userservice.endpoint}")
	private String endpoint;
	

	
	public UserModel save(UserModel user) {
		
		log.debug("Saving user: " + user);
		
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		requestHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		
		HttpEntity<UserModel> requestEntity = new HttpEntity<UserModel>(user, requestHeaders);
		UserModel savedUser = restTemplate.postForObject(endpoint+"/save", requestEntity, UserModel.class);
		
		return savedUser;
		
	}
	
	
	
	public List<UserModel> getAllUsers() {
		
		log.debug("Fetching all users");
		
		UserModel[] users = restTemplate.getForEntity(endpoint+"/all", UserModel[].class).getBody();
		return new ArrayList<UserModel>(Arrays.asList(users));
	}

}
