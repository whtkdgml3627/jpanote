package com.example.jpanote.exception;

public class BadCredentialsException extends RuntimeException {
	public BadCredentialsException() {
		super();
	}

	public BadCredentialsException(String message) {
		super(message);
	}
}