package com.relx.banking.authservice.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Naveen.Sankhala
 * Sep 25, 2025
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class AuthenticationException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }
	
	public AuthenticationException(String message) {
        super(message);
    }
}
