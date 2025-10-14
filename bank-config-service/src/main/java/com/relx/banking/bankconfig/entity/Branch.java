package com.relx.banking.bankconfig.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * @author Naveen.Sankhala
 * Oct 9, 2025
 */

@Data
@Entity
@Table(name="BRANCH")
public class Branch implements Serializable {

	private static final long serialVersionUID = -6094284855856618400L;
	
	@Id
	@Column(name = "Branch_Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long branchId;
	
	@Column(name = "Zr_Id")
	private Long zrId;
	
	@Column(name = "Branch_Code")
	private String branchCode;
	
	@Column(name = "Branch_Name")
	private String branchName;
	
	@Column(name = "Ifsc_Code")
	private String ifscCode;
	
	@Column(name = "Swift_Code")
	private String swiftCode;
	
	@Column(name = "Chk_Clearing_Code")
	private String chkClearingCode;
	
	@Column(name = "Address_Line1")
	private String addressLine1;
	
	@Column(name = "Address_Line2")
	private String addressLine2;
	
	@Column(name = "City_Id")
	private String cityId;
	
	@Column(name = "State_Id")
	private String stateId;
	
	@Column(name = "Zipcode")
	private String zipCode;
	
	@Column(name = "Country_Id")
	private String countryId;
	
	@Column(name = "Phone_Number")
	private String phoneNumber;
	
	@Column(name = "Email")
	private String email;
	
	@Column(name = "Status")
	private String status;
	
	@Column(name = "Branch_Type")
	private String branchType;
	
	@Column(name = "Manager_Id")
	private String ManagerId;
	
	@Column(name = "Is_Branch_Open")
	private String isBranchOpen;
	
	@Column(name = "Opening_Date")
	private String openingDate;
	
	@Column(name = "Closing_Date")
	private String closingDate;
	
	@Column(name = "Max_Cash_Limit")
	private String maxCashLimit;
	
	@Column(name = "Gst_No")
	private String gstNo;
	
	@Column(name = "Gst_Reg_Name")
	private String gstRegName;
	
	@Column(name = "Gst_Reg_Pan_No")
	private String gstRegPanNo;
	
	@Column(name = "Pool_Account")
	private String poolAccount;
	
	@Column(name="Created_By")
	private Long createdBy;

	@CreationTimestamp
	@Column(name="Created_Date")
	private LocalDateTime createdDate;
	
	@Column(name="Last_Chg_By")
	private Long lastChgBy;
	
	@UpdateTimestamp
	@Column(name="Last_Chg_Date")
	private LocalDateTime lastChgDate;

}
