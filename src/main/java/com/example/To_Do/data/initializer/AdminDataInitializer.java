package com.example.To_Do.data.initializer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.To_Do.Entity.Role;
import com.example.To_Do.Entity.User;
import com.example.To_Do.repo.RoleRepository;
import com.example.To_Do.repo.UserRepository;

import jakarta.transaction.Transactional;


@Component
public class AdminDataInitializer implements CommandLineRunner {
	
	@Autowired
	PasswordEncoder hashPassword;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	UserRepository userRepository;
	

	@Override
	@Transactional
	public void run(String... args) throws Exception {
		createAdmin();
		
		
	}
	
	public void createAdmin() {
		String email = "admin@gmail.com";
		if(!userRepository.existsByEmail(email)) {
			User user = new User();
			
			user.setFirstName("admin");
			user.setLastName("admin");
			user.setEmail(email);
			user.setPassword(hashPassword.encode("1234admin1234"));
			
			
			Role role = roleRepository.findByName("ADMIN");
			
			
			
	        if (role == null) {
	            System.err.println("✗ ERROR: ADMIN role not found! Check V2__insert_roles.sql migration.");
	            return;
	        }
			
			user.getRoles().add(role);
			role.getUsers().add(user);
			
			userRepository.save(user);
			
		}
		
	}

}
