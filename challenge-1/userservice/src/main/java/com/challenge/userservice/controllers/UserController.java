package com.challenge.userservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.userservice.entities.UserEntity;
import com.challenge.userservice.repos.UserRepository;

@RestController
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	
	@PostMapping(path="/save")
	public @ResponseBody String addNewUser (
			@RequestParam String name,
			@RequestParam String email,
			@RequestParam String description) {

		UserEntity user = new UserEntity();
		user.setName(name);
		user.setEmail(email);
		user.setDescription(description);
		
		userRepository.save(user);
		return "Saved";
	}
	
	
	
	
	@RequestMapping("/all")
	@ResponseBody
    public Iterable<UserEntity> getAll() {
		
		return userRepository.findAll();
    }

}
