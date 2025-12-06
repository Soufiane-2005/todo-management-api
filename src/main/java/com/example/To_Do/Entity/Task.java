package com.example.To_Do.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Task {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false , unique = true)
	private String titre;
	
	
	private String description;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TaskStatus status;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private TaskPriority priority;
	
	
	@ManyToOne
	@JoinColumn(name = "user_id" , nullable = false)
	@JsonIgnore
	private User user;

	
	
	
	
	
	

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTitre() {
		return titre;
	}


	public void setTitre(String titre) {
		this.titre = titre;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public TaskStatus getStatus() {
		return status;
	}


	public void setStatus(TaskStatus status) {
		this.status = status;
	}


	public TaskPriority getPriority() {
		return priority;
	}


	public void setPriority(TaskPriority priority) {
		this.priority = priority;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
	

	
	
	
	

	
	
}
