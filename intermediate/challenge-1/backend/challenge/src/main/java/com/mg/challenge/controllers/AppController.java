package com.mg.challenge.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class AppController {

	@GetMapping
	public String testApp() {
		return "Welcome to Bus Shifts Schedule";
	}
}
