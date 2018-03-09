package com.challenge.frontend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
	
	@GetMapping("/home")
    public String greeting(Model model) {
		
		System.out.println("Here");
		
        return "home";
    }
}
