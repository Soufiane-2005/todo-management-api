package com.example.To_Do.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.To_Do.Entity.User;
import com.example.To_Do.repo.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CustommerUserDetailsService implements UserDetailsService{
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email);
	    if(user == null) throw new UsernameNotFoundException("User not found: " + email);
	    return new org.springframework.security.core.userdetails.User(
	        user.getEmail(),
	        user.getPassword(),
	        user.getRoles().stream()
	            .map(role -> new SimpleGrantedAuthority(role.getName()))
	            .collect(Collectors.toList())
	    );
	}
	
	
	
	

}
