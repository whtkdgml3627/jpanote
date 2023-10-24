package com.example.jpanote.exception;

public class NoteNotFoundException extends RuntimeException {
	public NoteNotFoundException() {
		super();
	}

	public NoteNotFoundException(String message) {
		super(message);
	}
}