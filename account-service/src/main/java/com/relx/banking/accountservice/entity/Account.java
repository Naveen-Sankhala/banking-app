package com.relx.banking.accountservice.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Naveen Sankhala
 * Jun 3, 2025
 */

@Entity
@Table(name="Account")
@Data
@Builder @AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class Account extends AuditableEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Account_Id")
	private Long accountId;
	
	@Column(name = "Account_Number")
	private String accountNumber;
	
	@Column(name = "Customer_Id")
	private Long customerId;
	
	@Column(name = "Account_Type")
	private String accountType;
	
	@Column(name = "Currency_Code")
	private String currencyCode;
	
	@Builder.Default
	@Column(name = "Balance")
	private BigDecimal balance = BigDecimal.ZERO;
	
	@Column(name = "Status")
	private String status;
	
	@CreationTimestamp
	@Column(name = "Opened_Date")
	private LocalDate openedDate;
	
	@Column(name = "Closed_Date")
	private LocalDate closedDate;
	
	@Column(name = "Branch_Id")
	private Long branchId;
	
	// ✅ Optional fields
	@Builder.Default
    @Column(name = "Overdraft_Limit", precision = 18, scale = 2)
    private BigDecimal overdraftLimit = BigDecimal.ZERO;

    @Column(name = "IBAN_Number",length = 34)
    private String ibanNumber;

    @Builder.Default
    @Column(name = "Is_Joint_Account")
    private Character isJointAccount = 'N';
    
    @Builder.Default
    @Column(name = "Is_Chq_YN")
    private Character isChqYN = 'N';
       
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    private List<JointAccountHolder> jointHolders;
    
    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private AccountInformation accountInfo;
    
}
