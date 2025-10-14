package com.relx.banking.authservice.util;

import lombok.Getter;
import lombok.Setter;

/**
 * @author sankhaln
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
