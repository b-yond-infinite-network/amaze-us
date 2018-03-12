package com.challenge.userservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.userservice.entities.User;
import com.challenge.userservice.repos.UserRepository;

@RestController
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	

	
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public User addNewUser (@RequestBody User user) {

		return userRepository.save(user);
		
	}
	
	
	
	
	@RequestMapping("/all")
	@ResponseBody
    public Iterable<User> getAll() {
		
		return userRepository.findAll();
    }

}
