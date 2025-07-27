package com.example.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/public")
public class HomeController {
	
	@GetMapping("/home")
	public String getHome() {
		return "This is Home Page"; 
	}
	
	@GetMapping("/login")
	public String getLogin() {
		return "This is Login Page";
	}
	
	@GetMapping("/register")
	public String getRegister() {
		return "This is Register Page";
	}

}
