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
@Table(name="BANK_ZONE_REGION")
public class BankZoneRegion implements Serializable {

	private static final long serialVersionUID = 1274805701289764613L;
	
	@Id
	@Column(name = "Zr_Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bankZrId;
	
	@Column(name = "Bank_Id") 
	private Long bankId;
	
    @Column(name = "Parent_Id")
    private Long parentId;
    
    @Column(name = "Bank_Code")  
    private Long bankCode;
    
    @Column(name = "Zr_Type")  
    private Long zrType;
    
    @Column(name = "City_Code") 
    private Long cityCode;
    
    @Column(name = "Zr_Code")
    private Long zrCode;
    
    @Column(name = "Zr_Name") 
    private Long zrName;
    
    @Column(name = "Head_Office ")
    private Long headOffice;
    
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
