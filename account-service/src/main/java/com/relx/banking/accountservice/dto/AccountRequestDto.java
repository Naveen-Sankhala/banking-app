package com.relx.banking.accountservice.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Naveen Sankhala
 */
@Setter @Getter
public class AccountRequestDto implements Serializable {

	private static final long serialVersionUID = 2795365587074685730L;

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
	private Long lastChgBy;
	private AccInformationDto accInformation;
	private List<JointAccountHolderDto> jointHolders;

}
