package com.example.To_Do.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.To_Do.Entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}
