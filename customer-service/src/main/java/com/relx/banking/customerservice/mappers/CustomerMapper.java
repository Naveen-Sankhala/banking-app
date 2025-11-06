package com.relx.banking.customerservice.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.relx.banking.customerservice.dto.CustomerRequestDto;
import com.relx.banking.customerservice.entity.Customer;
import com.relx.banking.customerservice.enums.GenderEnum;

/**
 * @author Naveen.Sankhala
 * Sep 1, 2025
 */
@Mapper
public interface CustomerMapper {

	CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

	@Mapping(source = "customerId",target="customerId")
	@Mapping(source = "cifNo",target="customerIdentificationNo")
	@Mapping(source = "firstName",target="firstName")
	@Mapping(source = "middleName",target="middleName")    
	@Mapping(source = "lastName",target="lastName")      
	@Mapping(target = "gender", expression = "java(com.relx.banking.customerservice.enums.GenderEnum.fromString(custReqDto.getGender()))")
	@Mapping(source = "dob",target="dob")        
	@Mapping(source = "dateOfCreated",target="dateOfCreated")        
	@Mapping(source = "aadharNumber",target="aadharNumber")    
	@Mapping(source = "panNumber",target="panNumber")     
	@Mapping(source = "contactNo",target="contactNo")       
	@Mapping(source = "alternateContactNo",target="alternateContactNo") 
	@Mapping(source = "emailId",target="emailId")    
	@Mapping(source = "status",target="status") 
//	@Mapping(source = "createdBy",target="createdBy") 
//	@Mapping(source = "lastChgBy",target="lastChgBy") 
    Customer customerDTOToCustomer(CustomerRequestDto custReqDto);
}
