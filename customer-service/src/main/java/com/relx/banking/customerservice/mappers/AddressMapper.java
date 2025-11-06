package com.relx.banking.customerservice.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.relx.banking.customerservice.dto.CustomerAddressDto;
import com.relx.banking.customerservice.entity.Address;

/**
 * @author Naveen.Sankhala
 * Sep 8, 2025
 */
@Mapper
public interface AddressMapper {
	
	AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

	@Mapping(source = "addressType",target="addressType")
	@Mapping(source = "houseNumber",target="houseNumber")
	@Mapping(source = "addressLine1",target="addressLine1")
	@Mapping(source = "addressLine2",target="addressLine2")
	@Mapping(source = "addressLine3",target="addressLine3")
	@Mapping(source = "street",target="street")
	@Mapping(source = "cityId",target="cityId")
	@Mapping(source = "stateId",target="stateId")
	@Mapping(source = "zipcode",target="zipcode")
	Address customerAddressDTOToCustomerAddress(CustomerAddressDto addressDto);
	
}