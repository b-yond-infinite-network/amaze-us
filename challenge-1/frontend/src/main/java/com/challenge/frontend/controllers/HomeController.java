package com.challenge.frontend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.challenge.frontend.services.UserService;


@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/home")
    public String greeting(Model model) {
		
		
		model.addAttribute("allUsers", userService.getAllUsers());
		
		
        return "home";
    }
}
