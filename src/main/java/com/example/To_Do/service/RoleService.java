package com.example.To_Do.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.To_Do.Entity.Role;
import com.example.To_Do.repo.RoleRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RoleService {

	@Autowired 
	RoleRepository roleRepository;
	
	
	
	
	
	// create Roles: (this is for admin) : 
	
	
	
	
	public List<Role> getAllRoles(){
		return roleRepository.findAll();
	}
	
	public ResponseEntity<Map<String,String>> addRole(Role role) {
		role.setName(role.getName().toUpperCase());
		roleRepository.save(role);
		Map<String,String> response = new HashMap<>();
		response.put("success", "The role was created successfully");
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}


	public ResponseEntity<Map<String, String>> deleteRole(Long id) {
		
		Role role = roleRepository.findById(id).orElse(null);
		Map<String,String> response = new HashMap<>();
		if(role==null) {
			response.put("Error", "There is no Role with id = "+id);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		roleRepository.delete(role);
		response.put("success", "The role was deleted successfully");
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
	
	
	
}
