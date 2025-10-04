package com.example.Quora.Exceptions;

@SuppressWarnings("serial")
public class AnswerNotFoundException extends RuntimeException {

	public AnswerNotFoundException(String message) {
		super(message);
	}
}
