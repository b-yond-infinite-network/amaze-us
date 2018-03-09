package com.challenge.frontend.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.challenge.frontend.models.UserModel;



@Service
public class UserService {
	
	
	public void save(UserModel user) {
		
		// todo
		
	}
	
	
	public List<UserModel> getAllUsers() {
		
		List<UserModel> usersList = new ArrayList<>();
		
		usersList.add(new UserModel("musab", "hello@ddsd.com", "Test"));
		usersList.add(new UserModel("ali", "ali@ddsd.com", "sdkf lsdhf lsdhkfl sdf"));
		
		
		
		return usersList;
	}

}
