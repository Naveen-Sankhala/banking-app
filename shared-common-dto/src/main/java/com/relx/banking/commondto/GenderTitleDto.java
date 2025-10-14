package com.relx.banking.commondto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Naveen.Sankhala
 * Oct 10, 2025
 */
@Setter @Getter
public class GenderTitleDto implements Serializable {
	
	private static final long serialVersionUID = 1333079966253334724L;
	
	private Long id;
	private String genderCode;
	private String genderName;
	private String titleCode;
	private String titleName;
	private String status;

}
