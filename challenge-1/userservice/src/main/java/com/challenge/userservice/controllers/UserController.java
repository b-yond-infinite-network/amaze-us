package com.challenge.userservice.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.userservice.models.UserModel;

@RestController
public class UserController {
	
	
	@RequestMapping("/all")
    public List<UserModel> getAll() {
		
		
		List<UserModel> usersList = new ArrayList<>();
		
		// todo: fetch from db
		
		return usersList;
		
		
    }

}
