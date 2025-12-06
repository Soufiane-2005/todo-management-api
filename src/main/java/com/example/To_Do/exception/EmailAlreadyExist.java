package com.example.To_Do.exception;

public class EmailAlreadyExist extends RuntimeException{
	
	public EmailAlreadyExist(String message) {
		super(message);
	}
}
