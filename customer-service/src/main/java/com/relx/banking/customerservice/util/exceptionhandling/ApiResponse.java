package com.relx.banking.customerservice.util.exceptionhandling;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Naveen.Sankhala
 * Jun 3, 2025
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
