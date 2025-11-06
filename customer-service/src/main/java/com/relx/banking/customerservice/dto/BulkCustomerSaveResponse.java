package com.relx.banking.customerservice.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Naveen.Sankhala
 * Nov 3, 2025
 */
@Setter @Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BulkCustomerSaveResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<String> cifNo;
	private List<String> existCustomer;
	private List<String> error;

}
