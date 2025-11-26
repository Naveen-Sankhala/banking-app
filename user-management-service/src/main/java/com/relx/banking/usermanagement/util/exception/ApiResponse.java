package com.relx.banking.usermanagement.util.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Naveen.Sankhala
 * Nov 26, 2025
 */
@Setter @Getter
public class ApiResponse {
	private Boolean success;
	private String message;
	
	public ApiResponse(Boolean succeess, String message) {
		super();
		this.success = succeess;
		this.message = message;
	}
	
}
