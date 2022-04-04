package com.user.exceptions;

public class UnauthorizedException extends RuntimeException {
	private static final long serialVersionUID = -7695207062072110755L;

	public UnauthorizedException(String message) {
		super(message);
	}

}
