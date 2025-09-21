package com.relx.banking.accountservice.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * @author Naveen.Sankhala
 * Sep 11, 2025
 */
@Data
@Entity
@Table(name="Branch")
public class Branch implements Serializable {

	private static final long serialVersionUID = -4644989415023157401L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="Branch_Id")
    private Long branchId;
	
	@Column(name="Zr_Id")
	private Long zrId;

    @Column(name="Branch_Code",nullable = false, unique = true)
    private String branchCode;

    @Column(name="Branch_Name",nullable = false)
    private String branchName;

    @Column(name="Ifsc_Code")
    private String ifscCode;
    
    @Column(name="Swift_Code")
    private String swiftCode;

    @Column(name="Address_Line1")
    private String addressLine1;
    
    @Column(name="Address_Line2")
    private String addressLine2;
    
    @Column(name="City_Id")
    private Long cityId;
    
    @Column(name="State_Id")
    private Long stateId;
    
    @Column(name="Zipcode")
    private String zipcode;
    
    @Column(name="Country_Id")
    private Long countryId;
    
    @Column(name="Phone_Number")
    private String phoneNumber;
    
    @Column(name="Email")
    private String email;
    
    @Column(name="Status")
    private String status;

    @Column(name="Branch_Type")
    private String branchType;   // RETAIL, CORPORATE, etc.
//    
//    @Column(name="Region_Code")
//    private String regionCode;
    
    @Column(name="Manager_Id")
    private Long managerId;
    
    @Column(name="Is_Branch_Open")
    private String isBranchOpen;
    
    @Column(name="Opening_Date")
    private LocalDateTime openingDate;
    
    @Column(name="Closing_Date")
    private LocalDateTime closingDate;
    
    @Column(name="Max_Cash_Limit")
    private BigDecimal maxCashLimit = BigDecimal.ZERO;
    
    @Column(name="Gst_No")
    private String gstNo;
    
    @Column(name="Gst_Reg_Name")
    private String gstRegName;
    
    @Column(name="Gst_Reg_Pan_No")
    private String gstRegPanNo;
    
    @Column(name="Pool_Account")
    private String poolAccount;
    
    @Column(name="Created_By")
	private Long createdBy;
    
    @Column(name="Created_Date")
    private LocalDateTime createdDate;
    
    @Column(name="Last_Chg_By")
	private Long lastChgBy;
    
    @Column(name="Last_Chg_Date")
    private LocalDateTime lastChgDate;

    // relationships
    // One branch can have many accounts
//    @OneToMany(mappedBy = "branch", fetch = FetchType.LAZY)
//    private List<Account> accounts = new ArrayList<>();
}
