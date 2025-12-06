package com.example.To_Do.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.To_Do.Entity.User;

public interface UserRepository extends JpaRepository<User, Long>{

	boolean existsByEmail(String email);

	User findByEmail(String email);
	

}
