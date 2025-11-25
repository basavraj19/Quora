package com.example.Quora.Exceptions;

public class InvalidInputException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2748243627870465390L;

	public InvalidInputException(String message) {
		super(message);
	}
}
