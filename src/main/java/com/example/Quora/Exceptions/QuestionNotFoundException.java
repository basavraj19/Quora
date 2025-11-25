package com.example.Quora.Exceptions;

public class QuestionNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7981210478280577131L;

	public QuestionNotFoundException(String message) {
		super(message);
	}
}
