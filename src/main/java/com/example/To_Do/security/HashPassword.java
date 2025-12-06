package com.example.To_Do.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class HashPassword {
	
	@Bean
	PasswordEncoder hash() {
		return new BCryptPasswordEncoder();
	}
	

}
