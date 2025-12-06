package com.example.To_Do.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.To_Do.Entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(String string);

}
