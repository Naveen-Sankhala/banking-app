package com.relx.banking.accountservice.mappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import com.relx.banking.accountservice.dto.AccInformationDto;
import com.relx.banking.accountservice.entity.AccountInformation;

/**
 * @author Naveen.Sankhala
 * Sep 16, 2025
 */
@Mapper
public interface AccountInformationMapper {

	AccountInformationMapper INSTANCE = Mappers.getMapper(AccountInformationMapper.class);

	@Mapping(source = "customerId",target="customerId")
	@Mapping(source = "nameTitle",target="nameTitle")
	@Mapping(source = "accInfoName",target="accInfoName")    
	@Mapping(source = "accInfoGender",target="accInfoGender") 
	@Mapping(source = "dateOfBirth",target="dateOfBirth")  
	@Mapping(source = "accInfoAmount",target="accInfoAmount")        
	@Mapping(source = "accInfoStartDate",target="accInfoStartDate")        
	@Mapping(source = "accInfoEndDate",target="accInfoEndDate")    
	@Mapping(source = "accinfoStmtprnt",target="accinfoStmtprnt")       
	@Mapping(source = "accInfoDepNotice",target="accInfoDepNotice") 
	@Mapping(source = "accInfoLoanNotice",target="accInfoLoanNotice")   
	@Mapping(source = "accInfoRemarks",target="accInfoRemarks") 
	
	AccountInformation accInfoDtoToAccountInformation(AccInformationDto accInfoDto);
}
