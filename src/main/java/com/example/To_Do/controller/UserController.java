package com.example.To_Do.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.To_Do.Entity.Role;
import com.example.To_Do.dto.UserResponse;
import com.example.To_Do.service.UserService;

@RestController
public class UserController {
	

	@Autowired
	UserService userService;
	
	
	@GetMapping("/users")
	public List<UserResponse> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable Long id){
		return userService.deleteUser(id);
	}
	
	@GetMapping("/users/{id}/roles")
	public Set<Role> getRolesOfUser(@PathVariable Long id){
		return userService.getRoleOfUser(id);
	}
	
	@PostMapping("/users/{user_id}/roles")
	public ResponseEntity<?> addRoleToUser(@PathVariable Long user_id, @RequestBody Role role){
		return userService.addRoleToUser(user_id, role);
	}
	
	@DeleteMapping("/users/{user_id}/roles/{role_id}")
	public ResponseEntity<?> deleteRoleFromUser(@PathVariable Long user_id, @PathVariable Long role_id){
		return userService.deleteRoleFromUser(user_id, role_id);
	}
	

}
