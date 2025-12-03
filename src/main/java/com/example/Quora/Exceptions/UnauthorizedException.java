package com.example.Quora.Exceptions;

public class UnauthorizedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3578047216154021998L;

	public UnauthorizedException(final String message) {
		super(message);
	}
}
