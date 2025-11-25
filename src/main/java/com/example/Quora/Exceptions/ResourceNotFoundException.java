package com.example.Quora.Exceptions;

public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3784288872378922494L;

	public ResourceNotFoundException(String message) {
		super(message);
	}

}
