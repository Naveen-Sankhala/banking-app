package com.relx.banking.accountservice.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Naveen Sankhala
 */
@Setter @Getter
public class AccountResponseDto implements Serializable {

	private static final long serialVersionUID = 5040156079016333622L;
	
	private Long accountId;
	private String cifNo;
	private Long customerId;
	private String accountNumber;
	private String accountType;
	private String currencyCode;
	private BigDecimal balance;
	private String status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate openedDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDate closedDate;
	private Long branchId;
	private String branchCode;
	private BigDecimal overdraftLimit;
	private String ibanNumber;
	private Character isJointAccount;
	private Character  isChqYN;
	private Long createdBy;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDateTime createdDate;
	private Long lastChgBy;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	private LocalDateTime lastChgDate;
	private AccInformationDto accountInfo = new AccInformationDto();
	private List<JointAccountHolderDto> jointHolders = new ArrayList<JointAccountHolderDto>();
}
