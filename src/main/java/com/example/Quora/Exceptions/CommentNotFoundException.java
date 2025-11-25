package com.example.Quora.Exceptions;

public class CommentNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6696753125182981632L;

	public CommentNotFoundException(String message) {
		super(message);
	}
}
