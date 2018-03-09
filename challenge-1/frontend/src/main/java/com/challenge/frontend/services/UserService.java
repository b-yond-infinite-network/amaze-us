package com.challenge.frontend.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.challenge.frontend.models.UserModel;


/**
 * This service connects to the webservice endpoint to
 * deal with Users
 */
@Service
public class UserService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${userservice.endpoint}")
	private String endpoint;
	

	

	public void save(UserModel user) {
		
		log.debug("Saving user: " + user);
		
		// todo: Save to the endpoint
	}
	
	
	
	public List<UserModel> getAllUsers() {
		
		log.debug("Fetching all users");
		
		UserModel[] users = restTemplate.getForEntity(endpoint+"/all", UserModel[].class).getBody();
		return new ArrayList<UserModel>(Arrays.asList(users));
	}

}
