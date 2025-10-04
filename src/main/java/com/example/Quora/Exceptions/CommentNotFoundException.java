package com.example.Quora.Exceptions;

@SuppressWarnings("serial")
public class CommentNotFoundException extends RuntimeException {

	public CommentNotFoundException(String message) {
		super(message);
	}
}
