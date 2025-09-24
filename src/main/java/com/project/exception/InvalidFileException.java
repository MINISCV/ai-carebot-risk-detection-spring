package com.project.exception;

public class InvalidFileException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;

	public InvalidFileException(String message) {
		super(message);
	}

	public InvalidFileException(String message, Throwable cause) {
		super(message, cause);
	}
}