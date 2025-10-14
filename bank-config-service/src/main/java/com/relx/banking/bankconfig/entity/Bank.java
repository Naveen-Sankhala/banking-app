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
@Table(name="BANK")
public class Bank implements Serializable {

	private static final long serialVersionUID = 4578218350637384806L;
	
	@Id
	@Column(name = "Bank_Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bankId;
	
	@Column(name = "Bank_Code")
	private String bankCode;
	
    @Column(name = "Bank_Name")
    private String bankName;
    
    @Column(name = "Bank_HName")
    private String bankHName;
    
    @Column(name = "Bank_Type")
    private String bankType;
    
    @Column(name = "Bank_Head")
    private String bankHead;
    
    @Column(name = "Bank_Head_Office")
    private String bankHeadOffice;
    
    @Column(name = "Established_Date")
    private String establishedDate;
    
    @Column(name = "Mask_Code")
    private String maskCode;
    
    @Column(name = "Bill_Real_Head") 
    private String billRealHead;
    
    @Column(name = "Bill_Com_Head")	
    private String billComHead;
    
    @Column(name = "Country_Id")  
    private String countryId;
    
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
