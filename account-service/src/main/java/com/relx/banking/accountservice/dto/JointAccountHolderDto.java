package com.relx.banking.accountservice.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Naveen.Sankhala
 * Sep 12, 2025
 */

@Setter @Getter
public class JointAccountHolderDto implements Serializable {

	private static final long serialVersionUID = 4609939430071946712L;
	
	private Long customerId;
    private String holderType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate addedDate;
}
