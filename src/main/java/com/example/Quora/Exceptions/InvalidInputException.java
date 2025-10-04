package com.example.Quora.Exceptions;

@SuppressWarnings("serial")
public class InvalidInputException extends RuntimeException {

	public InvalidInputException(String message) {
		super(message);
	}
}
