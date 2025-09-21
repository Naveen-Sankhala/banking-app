package com.relx.banking.customerservice.util.exceptionhandling;

/**
 * @author Naveen.Sankhala
 * Jun 5, 2025
 */
public class InvalidCustomerException extends RuntimeException {

	private static final long serialVersionUID = -7756021987739055061L;

	public InvalidCustomerException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidCustomerException(String message) {
		super(message);
	}
	
	

}
