/**
 * 
 */
package com.relx.banking.accountservice.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Naveen.Sankhala
 * Sep 16, 2025
 */
@Data
@Entity
@Table(name = "Account_Information")
@Builder @AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class AccountInformation extends AuditableEntity implements Serializable {

	private static final long serialVersionUID = 1327374055854117887L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Accinfo_Id")
	private Long accInfoId;

	@Column(name = "Customer_Id")
	private Long customerId;

	@Column(name = "Name_Title")
	private String nameTitle;   // Mr, Ms, Dr, etc.

	@Column(name = "Accinfo_Name")
	private String accInfoName;
	
	@Column(name = "Gender")
	private String accInfoGender;

	@Column(name = "Date_Of_Birth")
	private LocalDate dateOfBirth;
	
	@Builder.Default
	@Column(name = "Accinfo_Amount")
	private BigDecimal accInfoAmount = BigDecimal.ZERO; ;
	
	@CreationTimestamp
	@Column(name = "Accinfo_Startdate")
	private LocalDate accInfoStartDate;
	
	@Column(name = "Accinfo_Enddate")
	private LocalDate accInfoEndDate;
	
	@Builder.Default
	@Column(name = "Accinfo_Stmtprnt")
	private Character accinfoStmtprnt = 'N';
	
	@Builder.Default
	@Column(name = "Accinfo_Depnotice")
	private Character accInfoDepNotice = 'N';
	
	@Builder.Default
	@Column(name = "Accinfo_Loannotice")
	private Character accInfoLoanNotice = 'N';
	
//	@Column(name = "AccInfo_Dispid")
//	private String accInfoDispid;
	
	@Column(name = "Accinfo_Remarks")
	private String accInfoRemarks;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "account_id", nullable = false)
	@JsonIgnore
	private Account account;

}
