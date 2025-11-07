package com.relx.banking.accountservice.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Naveen.Sankhala
 * Sep 17, 2025
 */
public interface AccountSummary {

	Long getcustomerId();
	Long getAccountId();
	String getAccountType();
	Long getAccountNumber();
	String getCurrencyCode();
	BigDecimal getBalance();
	Long getBranchId();
	AccountInfoView getAccountInfo();
	List<JointAccountView> getJointHolders();
	
	public interface AccountInfoView {
		String getAccInfoName();
		String getNameTitle();        
		String getAccInfoGender();       
		LocalDate getDateOfBirth();      
		BigDecimal getAccInfoAmount();   
		LocalDate getAccInfoStartDate();
		LocalDate getAccInfoEndDate();  
		Character getAccinfoStmtprnt(); 
		Character getAccInfoDepNotice(); 
		Character getAccInfoLoanNotice();
		String getaccInfoRemarks();      
	}

	public interface JointAccountView{
		Long getCustomerId();
	    String getHolderType();
	    LocalDate getAddedDate();
	}
}


