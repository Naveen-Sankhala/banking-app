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
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate dateOfCreated;
	private String nicNo;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate nicIssueDt;
	private String status;
	private List<CustomerAddressDto> address;
	private CustomerDetailsDto customerDetails;
	private Long createdBy;
	private Long lastChgBy;
	

}
