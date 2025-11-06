package com.relx.banking.customerservice.util.exceptionhandling;

/**
 * @author Naveen.Sankhala
 * Sep 10, 2025
 */
public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFoundException(String message) {
		super(message);
	}

	public NotFoundException(Throwable cause) {
		super(cause);
	}

}
