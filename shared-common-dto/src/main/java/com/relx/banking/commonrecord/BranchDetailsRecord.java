package com.relx.banking.commonrecord;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author Naveen.Sankhala
 * Oct 16, 2025
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record BranchDetailsRecord(
		Long branchId,         
		Long zrId,             
		String branchCode,     
		String branchName,     
		String ifscCode,       
		String swiftCode,      
		String chkClearingCode,
		String addressLine1,   
		String addressLine2,   
		Integer cityId,         
		String stateId,        
		String zipCode,        
		Integer countryId,      
		String phoneNumber,    
		String email,          
		String status,         
		String branchType,     
		Integer ManagerId,      
		String isBranchOpen,
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
		LocalDate openingDate, 
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
		LocalDate closingDate,    
		String maxCashLimit,   
		String gstNo,          
		String gstRegName,     
		String gstRegPanNo,    
		String poolAccount    ) {

}
