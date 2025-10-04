package com.example.Quora.Exceptions;

@SuppressWarnings("serial")
public class QuestionNotFoundException extends RuntimeException {

	public QuestionNotFoundException(String message) {
		super(message);
	}
}
