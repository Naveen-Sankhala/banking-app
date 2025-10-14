package com.relx.banking.customerservice.dto;

import java.util.List;

/**
 * @author Naveen.Sankhala
 * Sep 11, 2025
 */
public record CustomerDetailsRecord(
		Long customerId,
		String firstName,
		String lastName,
		String emailId,
		List<JointAccountDto> accounts
		) {}
