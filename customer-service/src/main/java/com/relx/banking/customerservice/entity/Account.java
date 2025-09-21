package com.relx.banking.customerservice.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Naveen Sankhala
 * Jun 3, 2025
 */
@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class Account implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String customerId;
	private String accountType;
	private String status;
	private double balance;
}
