package com.relx.banking.customerservice.dto;

/**
 * @author Naveen.Sankhala
 * Sep 11, 2025
 */
public record JointAccountDto(
		Long accountId,
		String accountNumber,
		String accountType,
		String holderType,
		String ibanNumber
		) {}
