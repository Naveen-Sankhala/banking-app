package com.relx.banking.commondto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


/**
 * @author Naveen.Sankhala
 * Oct 31, 2025
 */
@Setter @Getter
public class ReligionDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long religionId;
	private String rlgnEname;
	private String rlgnHname;
	private String rlgnCode;
	private String status;

}
