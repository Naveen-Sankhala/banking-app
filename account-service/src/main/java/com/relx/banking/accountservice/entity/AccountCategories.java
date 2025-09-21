package com.relx.banking.accountservice.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Naveen.Sankhala
 * Sep 15, 2025
 */
@Data
@Entity
@Table(name="Acc_Categories")
public class AccountCategories implements Serializable {

	private static final long serialVersionUID = 4377319452366825231L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Acc_Cat_Id")
	private Long accCatId;
	
	@Column(name = "Acc_Cat_Name")
	private String accCatName;
	
	@Column(name = "Acc_Cat_SName")
	private String accCatSName;
	
	@Column(name = "Acc_Cat_Code")
	private String accCatCode;
	
	@Column(name = "Category")
	private String category;
	
	@Column(name = "Status")
	private String status;
	
	@Column(name="Created_By")
	private Long createdBy;
    
    @Column(name="Created_Date")
    @CreationTimestamp
    private LocalDateTime createdDate;
    
    @Column(name="Last_Chg_By")
	private Long lastChgBy;
    
    @Column(name="Last_Chg_Date")
    @UpdateTimestamp
    private LocalDateTime lastChgDate;

}
