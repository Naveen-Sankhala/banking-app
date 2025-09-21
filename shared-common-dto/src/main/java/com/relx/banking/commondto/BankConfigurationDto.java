package com.relx.banking.commondto;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @author Naveen.Sankhala
 * Sep 19, 2025
 */
@Data
public class BankConfigurationDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long configId;
	private Long bankId; 
	private String englishName;
	private String hindiName;
	private Long countryId;
	private Long currencyId;
	private String allowMultipleCurrency;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate bankDate;
	private String dayEndProcess;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate financialYearStart;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate financialYearEnd;
	private String auditTrailRequired;
	private String auditOption;
	private Long auditNumber;
	private String singleModeEnabled;
	private String ecsBankCode;
	private String sameLoanAccountYn;
	private String clientCode;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate createdDate;
}
