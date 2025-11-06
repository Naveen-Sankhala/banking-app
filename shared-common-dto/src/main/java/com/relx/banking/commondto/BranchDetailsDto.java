package com.relx.banking.commondto;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	private Integer cityId;
	private Integer stateId;
	private String zipCode;
	private Integer countryId;
	private String phoneNumber;
	private String email;
	private String status;
	private String branchType;
	private Integer ManagerId;
	private String isBranchOpen;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate openingDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate closingDate;
	private String maxCashLimit;
	private String gstNo;
	private String gstRegName;
	private String gstRegPanNo;
	private String poolAccount;

}
