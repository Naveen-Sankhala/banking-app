package com.relx.banking.customerservice.entity;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.relx.banking.customerservice.enums.GenderEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
 * Oct 8, 2025
 */

@Entity
@Table(name = "CUSTOMER_DETAILS")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class CustomerDetails extends AuditableEntity implements Serializable {

	private static final long serialVersionUID = 2082864051945784426L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="Customer_Details_Id")
	private Long customerDetailsId;

	@Column(name = "Branch_Id")
	private Long branchId;

	@Enumerated(EnumType.STRING)
	@Column(name = "Gender")
	private GenderEnum  gender;

	@Column(name="Date_Of_Birth")
	private LocalDate dob;

	@Column(name = "Is_Minor")
	@Builder.Default
	private Character isMinor = 'N';

	@Column(name = "Major_Date")
	private LocalDate majorDate;

	@Column(name = "Marital_Status")
	private String maritalStatus;

	@Column(name = "Has_Guardian")
	@Builder.Default
	private Character hasGuardian = 'N';

	@Column(name = "Guardian_Type")
	private String guardianType;

	@Column(name = "Has_Nominee")
	@Builder.Default
	private Character hasNominee = 'N';

	@Column(name = "Occupation_Id")
	private Long occupationId;

	@Column(name = "Constitution_Id")
	private Long constitutionId;

	@Column(name = "Religion_Id")
	private Long religionId;

	@Column(name = "Caste_Id")
	private Long casteId;

	@Column(name = "Relation_Type")
	private String relationType;

	@Column(name = "Husband_Father_Title")
	private String husbandFatherTitle;

	@Column(name = "Husband_Father_Name")
	private String husbandFatherName;

	@Column(name = "Mother_Relation")
	private String motherRelation;

	@Column(name = "Mother_Title")
	private String motherTitle;

	@Column(name = "Mother_Name")
	private String motherName;

	@Column(name = "Education_Qual")
	private String educationQual;

	@Column(name = "Num_Dependents")
	private Integer numDependents;

	@Column(name = "Checksum_Value")
	private String checksumValue;

	@Column(name = "National_Id_Number")
	private String nationalIdNumber;

	@Column(name="Aadhar_Number")
	private String aadharNumber;

	@Column(name="Pan_Number")
	private String panNumber;

	@Column(name="Gst_In_Number")
	private String gstInNumber;

	@Column(name = "Passport_Number")
	private String passportNumber;

	@Column(name = "Passport_Place_Issue")
	private String passportPlaceIssue;

	@Column(name = "Passport_Issue_Date")
	private LocalDate passportIssueDate;

	@Column(name = "Passport_Expiry_Date")
	private LocalDate passportExpiryDate;

	@Column(name="Contact_No")
	private String contactNo;

	@Column(name="Alternate_Contact_No")
	private String alternateContactNo;

	@Column(name="EMAIL_ID")
	private String emailId;

	@Column(name = "Currency_Id")
	private Long currencyId;

	@Column(name = "Membership_Number")
	private String membershipNumber;

	@Column(name = "Employee_Number")
	private String employeeNumber;

	@Column(name = "Account_Manager")
	private String accountManager;

	@Column(name = "Customer_Group")
	private String customerGroup;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="Customer_Id",referencedColumnName ="Customer_Id")
	@JsonIgnore
	private Customer customer;
}
