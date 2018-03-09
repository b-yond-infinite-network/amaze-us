package com.challenge.frontend.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class WelcomeController {
	
	@RequestMapping("/")
	public String index(Model model) {
        return "index";
    }
	
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(HttpServletRequest request, 
	        @RequestParam(value="name") String name, 
	        @RequestParam(value="email") String email, 
	        @RequestParam(value="description") String description)
	        		throws ServletException, IOException {
		
		// Todo
		
		return null;

	}

}
