package com.relx.banking.commondto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Naveen.Sankhala
 * Oct 31, 2025
 */
@Getter @Setter
public class CastDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long castId;
	private String castEName;
	private String castHName;
	private String castCode;
	private String castType;
	private String status;

}
