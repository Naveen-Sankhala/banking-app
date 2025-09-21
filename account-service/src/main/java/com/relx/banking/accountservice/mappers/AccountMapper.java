package com.relx.banking.accountservice.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.relx.banking.accountservice.dto.AccountRequestDto;
import com.relx.banking.accountservice.entity.Account;

/**
 * @author Naveen.Sankhala
 * Sep 16, 2025
 */
@Mapper
public interface AccountMapper {

	AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

	@Mapping(source = "accountType",target="accountType")
	@Mapping(source = "currencyCode",target="currencyCode")
	@Mapping(source = "balance",target="balance")
	@Mapping(source = "status",target="status")    
	@Mapping(source = "openedDate",target="openedDate")        
	@Mapping(source = "closedDate",target="closedDate")        
	@Mapping(source = "branchId",target="branchId")    
	@Mapping(source = "overdraftLimit",target="overdraftLimit")     
	@Mapping(source = "ibanNumber",target="ibanNumber")       
	@Mapping(source = "isJointAccount",target="isJointAccount") 
	@Mapping(source = "isChqYN",target="isChqYN")    
	Account accountDtoToAccount(AccountRequestDto accReqDto);
}

//@Mapping(target = "gender", expression = "java(com.relx.banking.customerservice.enums.GenderEnum.fromString(custReqDto.getGender()))")
