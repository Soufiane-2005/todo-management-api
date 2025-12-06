package com.example.To_Do.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.To_Do.dto.UserLoginResponse;
import com.example.To_Do.dto.UserLoginRequest;
import com.example.To_Do.dto.UserRegisterRequest;
import com.example.To_Do.dto.UserRegisterResponse;
import com.example.To_Do.service.UserService;

@RestController
public class Controller {
	
	
	
	@Autowired
	UserService userService;
	
	
	@PostMapping("/register")
	
	public ResponseEntity<UserRegisterResponse> register(@RequestBody UserRegisterRequest request ){
		
		return userService.register(request);
	}
	
	@PostMapping("/login")
	
	public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest request){
		return userService.login(request);
	}
	
	
	

}
