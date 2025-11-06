package com.relx.banking.customerservice.dto;

import java.io.Serializable;

import com.relx.banking.customerservice.enums.AddressType;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Naveen.Sankhala
 * Sep 1, 2025
 */
@Setter @Getter
public class CustomerAddressDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long addressId;
	private String customerId;
	private AddressType addressType;
	private String houseNumber;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String street;
	private Integer cityId;
	private Integer stateId;
	private String zipcode;

}
