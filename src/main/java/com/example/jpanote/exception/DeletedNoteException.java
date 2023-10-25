package com.example.jpanote.exception;

public class DeletedNoteException extends RuntimeException {
	public DeletedNoteException() {
		super();
	}

	public DeletedNoteException(String message) {
		super(message);
	}
}