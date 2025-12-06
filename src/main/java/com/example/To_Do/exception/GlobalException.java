package com.example.To_Do.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
	
	@ExceptionHandler(EmailAlreadyExist.class)
	public ResponseEntity<?> handlEmailAlreadyExist(EmailAlreadyExist ex){
		Map<String,String> response = new HashMap<>();
		response.put("Error", ex.getMessage());
		
		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}
	
	@ExceptionHandler(FieldNullException.class)
	public ResponseEntity<?> handlFieldNull(FieldNullException ex){
		Map<String,String> response = new HashMap<>();
		response.put("Error", ex.getMessage());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}
	
	@ExceptionHandler(InvalidCredentials.class)
	public ResponseEntity<?> handlInvalidCredentials(InvalidCredentials ex){
		Map<String,String> response = new HashMap<>();
		response.put("Error", ex.getMessage());
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	}
	
	
	@ExceptionHandler(UserNotFound.class)
	public ResponseEntity<?> handlInvalidCredentials(UserNotFound ex){
		Map<String,String> response = new HashMap<>();
		response.put("Error", ex.getMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
}
