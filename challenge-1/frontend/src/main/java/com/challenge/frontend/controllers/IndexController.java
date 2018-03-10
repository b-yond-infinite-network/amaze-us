package com.challenge.frontend.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.challenge.frontend.models.UserModel;
import com.challenge.frontend.services.UserService;



@Controller
public class IndexController {
	
	@Autowired
	private UserService userService;


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
		
			
		userService.save(new UserModel(name, email, description));
			
		return "redirect:home";
	}

}
