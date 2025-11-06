package com.relx.banking.customerservice.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.relx.banking.customerservice.enums.AddressType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Naveen.Sankhala
 * Sep 2, 2025
 */

@Data
@Entity
@Table(name="Address")
@Builder @NoArgsConstructor @AllArgsConstructor
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="Address_Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long addressId;
		
	@Column(name="Address_Type")
	@Enumerated(EnumType.STRING)
	private AddressType addressType;
	
	@Column(name="House_Number")
	private String houseNumber;
	
	@Column(name="Address_Line1")
	private String addressLine1;
	
	@Column(name="Address_Line2")
	private String addressLine2;
	
	@Column(name="Address_Line3")
	private String addressLine3;
	
	@Column(name="Street")
	private String street;
	
	@Column(name="City_Id")
	private Integer cityId;
	
	@Column(name="State_Id")
	private Integer stateId;
	
	@Column(name="Zipcode")
	private String zipcode;
	
//	@Column(name="Country_Id")
//	private Long countryId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="Customer_Id",referencedColumnName ="Customer_Id")
	@JsonIgnore
	private Customer customer;

}
