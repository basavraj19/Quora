package com.example.Quora.Exceptions;

public class DuplicateEntryException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2876426487195881035L;

	public DuplicateEntryException(String message) {
		super(message);
	}

}
