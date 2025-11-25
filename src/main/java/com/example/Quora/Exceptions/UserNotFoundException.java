package com.example.Quora.Exceptions;

public class UserNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3199078537560423285L;

	public UserNotFoundException(String message) {
		super(message);
	}

}
