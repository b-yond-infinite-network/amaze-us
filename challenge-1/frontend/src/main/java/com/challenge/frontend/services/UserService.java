package com.challenge.frontend.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.challenge.frontend.models.UserModel;



@Service
public class UserService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	public void save(UserModel user) {
		
		log.debug("Saving user: " + user);
		
		// todo: Save to the endpoint
	}
	
	
	public List<UserModel> getAllUsers() {
		
		List<UserModel> usersList = new ArrayList<>();
		
		// todo: fetch from endpoint
		
		usersList.add(new UserModel("musab", "hello@ddsd.com", "Test"));
		usersList.add(new UserModel("ali", "ali@ddsd.com", "sdkf lsdhf lsdhkfl sdf"));
		
		log.debug("Fetched users: " + usersList);
		
		return usersList;
	}

}
