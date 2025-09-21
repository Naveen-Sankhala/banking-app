package com.relx.banking.accountservice.util.exceptionhandling;

/**
 * @author Naveen.Sankhala
 * Sep 16, 2025
 */
public class AccountAlreadyExistsException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public AccountAlreadyExistsException() {
		super();
	}

	public AccountAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccountAlreadyExistsException(String message) {
		super(message);
	}

	public AccountAlreadyExistsException(Throwable cause) {
		super(cause);
	}
	
	

}
