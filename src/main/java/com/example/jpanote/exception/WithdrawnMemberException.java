package com.example.jpanote.exception;

public class WithdrawnMemberException extends RuntimeException {
	public WithdrawnMemberException() {
		super();
	}

	public WithdrawnMemberException(String message) {
		super(message);
	}
}