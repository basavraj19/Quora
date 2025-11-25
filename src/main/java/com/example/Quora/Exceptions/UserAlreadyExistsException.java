package com.example.Quora.Exceptions;

public class UserAlreadyExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7324266236412053050L;

	public UserAlreadyExistsException(String message) {
		super(message);
	}
}