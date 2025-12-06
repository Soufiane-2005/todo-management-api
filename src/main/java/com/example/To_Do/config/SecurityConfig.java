package com.example.To_Do.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.To_Do.security.JwtFilter;



@Configuration
public class SecurityConfig {
	
	
	
	
    
    @Autowired
    JwtFilter jwtFilter;
    

	@Bean
	SecurityFilterChain seucrityconfig(HttpSecurity http) throws Exception {
		
		
		http.csrf(csrf->csrf.disable());
		http.formLogin(form -> form.disable());
		http.httpBasic(basic -> basic.disable());
		http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.authorizeHttpRequests(auth -> auth
				
				
				.requestMatchers("/login", "/register").permitAll()
				
				
				
				

                .requestMatchers(HttpMethod.POST, "/users/*/tasks").hasAnyAuthority("USER","ADMIN")
                .requestMatchers(HttpMethod.GET, "/users/*/tasks").hasAnyAuthority("USER","ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/users/*/tasks/*").hasAnyAuthority("USER","ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/users/*/tasks/*").hasAnyAuthority("USER","ADMIN")

                .requestMatchers("/roles/**").hasAuthority("ADMIN")
                .requestMatchers("/users/**").hasAuthority("ADMIN")
                .requestMatchers("/tasks").hasAuthority("ADMIN")

                .anyRequest().authenticated());
		
		
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		
		
		return http.build();
	}
	
}
































