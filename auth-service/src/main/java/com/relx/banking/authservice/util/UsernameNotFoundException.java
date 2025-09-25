package com.relx.banking.authservice.util;

/**
 * @author Naveen.Sankhala
 * Sep 24, 2025
 */
public class UsernameNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UsernameNotFoundException() {
		super();
	}

	public UsernameNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public UsernameNotFoundException(String message) {
		super(message);
	}

	public UsernameNotFoundException(Throwable cause) {
		super(cause);
	}
	
	

}
