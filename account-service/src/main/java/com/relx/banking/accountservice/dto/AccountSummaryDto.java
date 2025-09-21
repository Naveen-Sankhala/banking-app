package com.relx.banking.accountservice.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * @author Naveen.Sankhala
 * Sep 17, 2025
 */
@Getter
@AllArgsConstructor
public class AccountSummaryDto implements Serializable {

	private static final long serialVersionUID = -6463997202591474551L;
	
	private Long accountId;
    private BigDecimal balance;
    private Long branchId; // from AccountInformation, as example
    private String title; 
    private String name;

//    public AccountSummaryDto(Long accountId, BigDecimal balance, String branchCode,String title) {
//        this.accountId = accountId;
//        this.balance = balance;
//        this.branchCode = branchCode;
//    }

}
