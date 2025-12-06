package com.example.To_Do.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.To_Do.Entity.Task;
import com.example.To_Do.Entity.TaskPriority;
import com.example.To_Do.Entity.TaskStatus;
import com.example.To_Do.Entity.User;
import com.example.To_Do.exception.UserNotFound;
import com.example.To_Do.repo.TaskRepository;
import com.example.To_Do.repo.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TaskService {
	
	@Autowired
	TaskRepository taskRepository;
	@Autowired
	UserRepository userRepository;

	public List<Task> getAllTasks() {
		return taskRepository.findAll();
	}

	public Task addTaskToUser(Long id, Task body) {
		
		User user = userRepository.findById(id).orElse(null);
		
		if(user==null) {
			throw new UserNotFound("User not found");
		}
		body.setPriority(body.getPriority()==null?TaskPriority.LOW:body.getPriority());
		body.setStatus(body.getStatus()==null?TaskStatus.TODO:body.getStatus());
		body.setUser(user);

		Task task = taskRepository.save(body);
		user.getTasks().add(task);
		
		
		return task;
		
		
		
		
	}

	

	public ResponseEntity<?> deleteTaskOfUser(Long user_id, Long task_id) {
		
		User user = userRepository.findById(user_id).orElse(null);
		
		Map<String,String> response = new HashMap<>();
		
		if(user==null) {
			response.put("Error", "User not found");
			ResponseEntity.badRequest().body(response);
		}
		Task task = taskRepository.findById(task_id).orElse(null);
		if(task==null) {
			response.put("Error", "Task not found");
			ResponseEntity.badRequest().body(response);
		}
		
		user.getTasks().remove(task);
		taskRepository.delete(task);
		response.put("Success", "The task has been deleted successfully");
		return ResponseEntity.ok(response);
	}

	public ResponseEntity<?> updateTask(Long user_id,Long task_id, Task request){
		
		User user = userRepository.findById(user_id).orElse(null);
		
		Map<String,String> response = new HashMap<>();
		
		if(user==null) {
			response.put("Error", "User not found");
			return ResponseEntity.badRequest().body(response);
		}
		Task task = taskRepository.findById(task_id).orElse(null);
		if(task==null) {
			response.put("Error", "Task not found");
			return ResponseEntity.badRequest().body(response);
		}
		
		if(!task.getUser().getId().equals(user_id)) {
			response.put("Error", "Task doesn't belong to this user");
			return ResponseEntity.badRequest().body(response);
		}
		
		if(request.getDescription()!=null) task.setDescription(request.getDescription());
		if(request.getPriority()!=null) task.setPriority(request.getPriority());
		if(request.getStatus()!=null) task.setStatus(request.getStatus());
		if(request.getTitre()!=null) task.setTitre(request.getTitre());
		
		return ResponseEntity.ok().body(task);
		
	}

	

	public Set<Task> getTasksWithPriorityOrStatus(Long user_id, TaskPriority priority, TaskStatus status) {
		
		
		User user = userRepository.findById(user_id).orElse(null);
		
		
		if(user==null) {
			throw new UserNotFound("User not found");
		}
		if(priority==null && status == null) {
			return user.getTasks();
		}
		if(priority==null) {
			return user.getTasks().stream().filter(x->x.getStatus().equals(status))
					.collect(Collectors.toSet());
		}
		if(status==null) {
			return user.getTasks().stream().filter(x->x.getPriority().equals(priority))
					.collect(Collectors.toSet());
		}
		return user.getTasks().stream().filter(x->x.getPriority().equals(priority) && x.getStatus()
				.equals(status))
				.collect(Collectors.toSet());
		
		

	}

	
	
	
	

}
