package com.relx.banking.customerservice.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Naveen Sankhala
 */
@Setter @Getter
public class CustomerRequestDto implements Serializable {

	private static final long serialVersionUID = 2795365587074685730L;
	
	private Long customerId;
	private String custTitle;
	private String firstName;
	private String middleName;
	private String lastName;
	private String cifNo;
	private String gender;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate dob;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate dateOfCreated;
	private String aadharNumber;
	private String panNumber;
	//private String gstNumber
	private String contactNo;
	private String alternateContactNo;
	private String emailId;
	private String status;
	//private String nicNo
	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	//private LocalDate nicIssueDt
	private List<CustomerAddressDto> address;
	private CustomerDetailsDto customerDetails;
	private Long createdBy;
	private Long lastChgBy;
	

}
