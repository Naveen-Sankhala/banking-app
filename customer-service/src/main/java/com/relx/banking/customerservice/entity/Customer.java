package com.relx.banking.customerservice.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.relx.banking.customerservice.enums.GenderEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Naveen.Sankhala
 * Sep 1, 2025
 */
@Data
@Entity
@Table(name="Customer")
@Builder @NoArgsConstructor @AllArgsConstructor
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Customer_Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerId;

	@Column(name = "CIF_NO", unique = true, nullable = false, insertable = false, updatable = false,length = 50)
	private String customerIdentificationNo;

	@Column(name="First_Name")
	private String firstName;

	@Column(name="Middle_Name")
	private String middleName;

	@Column(name="Last_Name")
	private String lastName;

	@Enumerated(EnumType.STRING)
	@Column(name = "Gender")
	private GenderEnum  gender;
	
	@Column(name="Date_Of_Birth")
	private LocalDate dob;

	@Column(name="Aadhar_Number")
	private String aadharNumber;

	@Column(name="Pan_Number")
	private String panNumber;

	@Column(name="Contact_No")
	private String contactNo;

	@Column(name="Alternate_Contact_No")
	private String alternateContactNo;

	@Column(name="EMAIL_ID")
	private String emailId;

	@Column(name="Date_Of_Created")
	private LocalDate dateOfCreated;

	@Column(name="Date_Of_Inactive")
	private LocalDate dateOfInactive;

	@Column(name="Status")
	private String status;

	@OneToMany(mappedBy="customer",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Address> address;
	
//	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
//	private List<JointAccountHolder> jointAccounts = new ArrayList<>();


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
