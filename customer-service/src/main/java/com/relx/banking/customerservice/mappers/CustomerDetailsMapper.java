package com.relx.banking.customerservice.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.relx.banking.customerservice.dto.CustomerDetailsDto;
import com.relx.banking.customerservice.entity.CustomerDetails;

/**
 * @author Naveen.Sankhala
 * Oct 8, 2025
 */
@Mapper
public interface CustomerDetailsMapper {

	CustomerDetailsMapper INSTANCE = Mappers.getMapper(CustomerDetailsMapper.class);

	@Mapping(source = "branchId",target="branchId")
	@Mapping(source = "isMinor",target="isMinor")
	@Mapping(source = "majorDate",target="majorDate")
	@Mapping(source = "hasGuardian",target="hasGuardian")
	@Mapping(source = "hasNominee",target="hasNominee")
	@Mapping(source = "occupationId",target="occupationId")
	@Mapping(source = "constitutionId",target="constitutionId")
	@Mapping(source = "religionId",target="religionId")
	@Mapping(source = "casteId",target="casteId")
	@Mapping(source = "relationType",target="relationType")
	@Mapping(source = "husbandFatherTitle",target="husbandFatherTitle")
	@Mapping(source = "husbandFatherName",target="husbandFatherName")
	
	@Mapping(source = "motherRelation",target="motherRelation")
	@Mapping(source = "motherTitle",target="motherTitle")
	@Mapping(source = "motherName",target="motherName")
	
	@Mapping(source = "maritalStatus",target="maritalStatus")
	@Mapping(source = "numDependents",target="numDependents")
	@Mapping(source = "guardianType",target="guardianType")
	@Mapping(source = "checksumValue",target="checksumValue")
	@Mapping(source = "nationalIdNumber",target="nationalIdNumber")
	@Mapping(source = "passportNumber",target="passportNumber")
	@Mapping(source = "passportPlaceIssue",target="passportPlaceIssue")
	@Mapping(source = "passportIssueDate",target="passportIssueDate")
	@Mapping(source = "passportExpiryDate",target="passportExpiryDate")
	@Mapping(source = "currencyId",target="currencyId")
	@Mapping(source = "membershipNumber",target="membershipNumber")
	@Mapping(source = "employeeNumber",target="employeeNumber")
	@Mapping(source = "accountManager",target="accountManager")
	@Mapping(source = "customerGroup",target="customerGroup")
	
	CustomerDetails customerDetailsDTOToCustomerDetails(CustomerDetailsDto custDetailsDto);
}
