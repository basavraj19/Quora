package com.example.Quora.Exceptions;

public class AnswerNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1687901023056887733L;

	public AnswerNotFoundException(String message) {
		super(message);
	}
}
