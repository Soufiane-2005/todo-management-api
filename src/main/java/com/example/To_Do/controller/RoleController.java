package com.example.To_Do.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.To_Do.Entity.Role;
import com.example.To_Do.service.RoleService;
import com.example.To_Do.service.UserService;

@RestController
public class RoleController {
	
	
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/roles")
	public List<Role> getAllRoles(){
		return roleService.getAllRoles();
	}
	
	@PostMapping("/roles")
	public ResponseEntity<Map<String,String>> addRole(@RequestBody Role role){
		return roleService.addRole(role);
	}
	
	@DeleteMapping("/roles/{id}")
	public ResponseEntity<Map<String,String>> deleteRole(@PathVariable Long id){
		return roleService.deleteRole(id);
	}
	

}
