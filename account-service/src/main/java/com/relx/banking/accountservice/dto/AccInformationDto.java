package com.relx.banking.accountservice.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.mapstruct.Mapping;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Naveen.Sankhala
 * Sep 16, 2025
 */
@Setter @Getter 
public class AccInformationDto implements Serializable {
	
	private static final long serialVersionUID = 1266820111959201861L;
	
	private Long customerId;
	private Long accountId;
	private String nameTitle;
	private String accInfoName;
	private String accInfoGender;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate dateOfBirth;
	private BigDecimal accInfoAmount;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate accInfoStartDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate accInfoEndDate;
	private Character accinfoStmtprnt;
	private Character accInfoDepNotice;
	private Character accInfoLoanNotice;
	private String accInfoRemarks;

}
