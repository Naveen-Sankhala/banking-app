package com.relx.banking.commondto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Naveen.Sankhala
 * Oct 10; 2025
 */
@Getter @Setter
public class BranchDetailsDto implements Serializable{
	
	private static final long serialVersionUID = 5214540899438796432L;
	
	private Long branchId;
	private Long zrId;
	private String branchCode;
	private String branchName;
	private String ifscCode;
	private String swiftCode;
	private String chkClearingCode;
	private String addressLine1;
	private String addressLine2;
	private String cityId;
	private String stateId;
	private String zipCode;
	private String countryId;
	private String phoneNumber;
	private String email;
	private String status;
	private String branchType;
	private String ManagerId;
	private String isBranchOpen;
	private String openingDate;
	private String closingDate;
	private String maxCashLimit;
	private String gstNo;
	private String gstRegName;
	private String gstRegPanNo;
	private String poolAccount;

}
