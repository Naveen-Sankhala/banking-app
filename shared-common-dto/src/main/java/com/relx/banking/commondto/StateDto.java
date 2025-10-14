package com.relx.banking.commondto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Naveen.Sankhala
 * Oct 10, 2025
 */
@Getter @Setter
public class StateDto implements Serializable {

	private static final long serialVersionUID = -6684773367483913728L;
	
	private Long stateId;
	private String stateName;
	private String stateCode;
	private Long countryId;

}
