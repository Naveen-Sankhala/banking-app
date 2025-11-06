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
	private Long occupationId;
	private Long constitutionId;
	private Character isMinor = 'N';
	private Character hasGuardian = 'N';
	private Character hasNominee = 'N';
	private Long religionId;
	private Long casteId;
	private String husbandFatherTitle;
	private String husbandFatherName;
	private String relationType;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate majorDate;
	private String motherTitle;
	private String motherName;
	private String motherRelation;
	private String maritalStatus;
	private String educationQual;
	private Integer numDependents;
	private String guardianType;
	private String categoryCode;
	private String introductionType;
	private String checksumValue;
	private String nationalIdNumber;
	private String passportNumber;
	private String passportPlaceIssue;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate passportIssueDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate passportExpiryDate;
	private Long currencyId;
	private String membershipNumber;
	private String employeeNumber;
	private String accountManager;
	private String customerGroup;

}
