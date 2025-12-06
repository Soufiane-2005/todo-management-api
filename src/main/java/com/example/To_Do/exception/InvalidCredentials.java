package com.example.To_Do.exception;

public class InvalidCredentials extends RuntimeException{
	public InvalidCredentials(String msg) {
		super(msg);
	}

}
