package com.relx.banking.customerservice.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.relx.banking.customerservice.entity.Address;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Naveen Sankhala
 */
@Setter @Getter
public class CustomerResponseDto implements Serializable {

	private static final long serialVersionUID = 5040156079016333622L;
	
	private Long customerId;
	private String cifNo;
	private String firstName;
	private String middleName;
	private String lastName;
	private String  gender;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate dob;
	private String aadharNumber;
	private String panNumber;
	private String contactNo;
	private String alternateContactNo;
	private String emailId;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate dateOfCreated;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate dateOfInactive;
	private String status;
	private List<Address> address;
	private Long createdBy;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDateTime createdDate;
	private Long lastChgBy;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDateTime lastChgDate;

}
