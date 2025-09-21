package com.relx.banking.accountservice.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Naveen.Sankhala
 * Sep 11, 2025
 */
public record CustomerDto(
		Long customerId,
		String cifNo,
		String firstName,
		String lastName,
		String  gender,
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
		LocalDate dob,
		String aadharNumber,
		String panNumber,
		String contactNo,
		String alternateContactNo,
		String emailId,
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
		LocalDate dateOfCreated,
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
		LocalDate dateOfInactive,
		String status
		) {

}




