package com.example.To_Do.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.To_Do.Entity.Role;
import com.example.To_Do.Entity.User;
import com.example.To_Do.dto.UserLoginRequest;
import com.example.To_Do.dto.UserLoginResponse;
import com.example.To_Do.dto.UserRegisterRequest;
import com.example.To_Do.dto.UserRegisterResponse;
import com.example.To_Do.dto.UserResponse;
import com.example.To_Do.exception.EmailAlreadyExist;
import com.example.To_Do.exception.FieldNullException;
import com.example.To_Do.exception.InvalidCredentials;
import com.example.To_Do.exception.UserNotFound;
import com.example.To_Do.repo.RoleRepository;
import com.example.To_Do.repo.UserRepository;
import com.example.To_Do.security.JwtUtil;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder hash;
	
	@Autowired
	
	JwtUtil jwtUtil;
	
	
	
	
	
	public ResponseEntity<UserRegisterResponse> register(UserRegisterRequest request) {
		
		
		if(userRepository.existsByEmail(request.getEmail())) {
			throw new EmailAlreadyExist("This email already exist");
		}
		
		if((request.getEmail()==null) || (request.getFirstName()==null) || (request.getLastName()==null) || 
				(request.getPassword()==null)) {
			throw new FieldNullException("A field is missing");
		}
		
		
		
		User user = new User();
		user.setEmail(request.getEmail());
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setPassword(hash.encode(request.getPassword()));
		user.getRoles().add(roleRepository.findByName("user"));
		
		User savedUser = userRepository.save(user);
		
		UserRegisterResponse response = new UserRegisterResponse();
		
		response.setEmail(savedUser.getEmail());
		response.setId(savedUser.getId());
		response.setFirstName(savedUser.getFirstName());
		response.setLastName(savedUser.getLastName());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
		
		
	}





	public ResponseEntity<UserLoginResponse> login(UserLoginRequest request) {
		
		if(!userRepository.existsByEmail(request.getEmail())) {
			throw new UserNotFound("Invalid Credentials");
		}
		
		User user = userRepository.findByEmail(request.getEmail());
		
		
		if(!hash.matches(request.getPassword(), user.getPassword())) {
			throw new InvalidCredentials("Invalid Credentials");
		}
		
		UserLoginResponse response = new UserLoginResponse();
		
		response.setEmail(user.getEmail());
		response.setId(user.getId());
		response.setToken(jwtUtil.generateToken(user));
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
		
		
		
	}





	public List<UserResponse> getAllUsers() {
		List<UserResponse> list = new ArrayList<>();
		userRepository.findAll().stream().forEach(u->{
			UserResponse user = new UserResponse();
			user.setEmail(u.getEmail());
			user.setFirstName(u.getFirstName());
			user.setLastName(u.getLastName());
			user.setId(u.getId());
			user.setRoles(u.getRoles());
			list.add(user);
		});
			
		
		return list;
		
	}





	public ResponseEntity<?> deleteUser(Long id) {
		User user = userRepository.findById(id).orElse(null);
		Map<String,String> response = new HashMap<>();

		if(user==null) {
			response.put("Error", "No user with id = "+id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
		userRepository.deleteById(id);
		response.put("success", "The user was deleted successfully");
		
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}





	public Set<Role> getRoleOfUser(Long id) {
		
		User user =  userRepository.findById(id).orElse(null);
		if(user==null) {
			throw new UserNotFound("User not found");
		}
		return user.getRoles();
	}





	public ResponseEntity<?> addRoleToUser(Long user_id, Role request) {
		User user = userRepository.findById(user_id).orElse(null);
		
		if(user == null) {
			throw new UserNotFound("User not found");
		}
		Role role = roleRepository.findByName(request.getName().toUpperCase());
		
		if(role == null) {
			Map<String,String> response = new HashMap<>();
			response.put("Error","Role not found");
			
			return ResponseEntity.badRequest().body(response);
		}
		
		user.getRoles().add(role);
		
		return ResponseEntity.ok().body(user.getRoles());
	}





	public ResponseEntity<?> deleteRoleFromUser(Long user_id, Long role_id) {

		User user = userRepository.findById(user_id).orElse(null);
		Role role = roleRepository.findById(role_id).orElse(null);
		Map<String, String> response = new HashMap<>();
		if(user==null) {
			response.put("Error","User not found");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		
		if(role == null) {
			response.put("Error","Role not found");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

		}
		
		if(!user.getRoles().contains(role)) {
			response.put("Error","The user doesn't have this role");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		
		user.getRoles().remove(role);
		role.getUsers().remove(user);
		response.put("success", "Role " + role.getName() + " has been removed from the user.");
		return ResponseEntity.ok(response);

		
		
		
		
		
	}
	
	
	


}
