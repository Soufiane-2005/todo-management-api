package com.example.To_Do.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.To_Do.Entity.Task;
import com.example.To_Do.Entity.TaskPriority;
import com.example.To_Do.Entity.TaskStatus;
import com.example.To_Do.service.TaskService;

@RestController
public class TaskController {
	
	@Autowired
	TaskService taskService;
	
	@GetMapping("/tasks")
	public List<Task> getAllTasks(){
		return taskService.getAllTasks();
	}

	
	@PostMapping("/users/{id}/tasks")
	public Task addTaskToUser(@PathVariable Long id, @RequestBody Task body) {
		return taskService.addTaskToUser(id, body);
	}
	
	
	@DeleteMapping("/users/{user_id}/tasks/{task_id}")
	public ResponseEntity<?> deleteTaskOfUser(@PathVariable Long user_id, @PathVariable Long task_id){
		return taskService.deleteTaskOfUser(user_id, task_id);
		
	}
	
	@PatchMapping("/users/{user_id}/tasks/{task_id}")
	public ResponseEntity<?> updateTask(@PathVariable Long user_id,@PathVariable Long task_id, @RequestBody Task request) {
		return taskService.updateTask(user_id,task_id, request);
	}
	
	@GetMapping("/users/{user_id}/tasks")
	public Set<Task> getTasksWithPriorityOrStatus(@PathVariable Long user_id, @RequestParam (required = false) TaskPriority priority,
			@RequestParam(required = false) TaskStatus status ){
		return taskService.getTasksWithPriorityOrStatus(user_id,priority,status);
	}
	
}
