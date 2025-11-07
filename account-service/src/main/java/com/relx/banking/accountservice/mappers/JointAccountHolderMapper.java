package com.relx.banking.accountservice.mappers;

import java.time.LocalDate;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.relx.banking.accountservice.dto.AccountRequestDto;
import com.relx.banking.accountservice.dto.JointAccountHolderDto;
import com.relx.banking.accountservice.entity.Account;
import com.relx.banking.accountservice.entity.JointAccountHolder;

/**
 * @author Naveen.Sankhala
 * Sep 16, 2025
 */
@Mapper
public interface JointAccountHolderMapper {
	
	JointAccountHolderMapper INSTANCE = Mappers.getMapper(JointAccountHolderMapper.class);
	
	@Mapping(source = "customerId",target="customerId")
	@Mapping(source = "holderType",target="holderType")
    @Mapping(source = "addedDate",target="addedDate")    
    JointAccountHolder jointAccHoldDtoToJAccHolder(JointAccountHolderDto jAccHDto);

}
