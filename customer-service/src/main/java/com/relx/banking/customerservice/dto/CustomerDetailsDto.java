package com.relx.banking.customerservice.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Naveen.Sankhala
 * Oct 8, 2025
 */
@Setter @Getter
public class CustomerDetailsDto implements Serializable {

	private static final long serialVersionUID = -3154074074477253133L;

	private Long customerDetailsId;
	private Long customerId;
	private Long branchId;
	private String gender;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate dob;
	
	private Character isMinor = 'N';
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate majorDate;
	
	private String maritalStatus;
	private Character hasGuardian = 'N';
	private String guardianType;
	private Character hasNominee = 'N';
	
	private Long occupationId;
	private Long constitutionId;
	private Long religionId;
	private Long casteId;
	
	private String relationType;
	private String husbandFatherTitle;
	private String husbandFatherName;
	
	private String motherRelation;
	private String motherTitle;
	private String motherName;
	
	private String educationQual;
	private Integer numDependents;
	
	private String checksumValue;
	
	private String categoryCode;
	private String introductionType;
	
	private String nationalIdNumber;
	private String aadharNumber;
	private String panNumber;
	private String gstInNumber;
	private String passportNumber;
	private String passportPlaceIssue;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate passportIssueDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate passportExpiryDate;
	private String contactNo;
	private String alternateContactNo;
	private String emailId;
	private Long currencyId;
	private String membershipNumber;
	private String employeeNumber;
	private String accountManager;
	private String customerGroup;

}
