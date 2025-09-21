package com.relx.banking.accountservice.dto;

import java.math.BigDecimal;

/**
 * @author Naveen.Sankhala
 * Sep 17, 2025
 */
public interface AccountSummary {

	Long getAccountId();
	BigDecimal getBalance();
	String getAccInfoName();
	String getNameTitle();
}
