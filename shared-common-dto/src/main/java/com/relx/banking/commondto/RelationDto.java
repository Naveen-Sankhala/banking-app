package com.relx.banking.commondto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Naveen.Sankhala
 * Oct 10, 2025
 */
@Getter @Setter
public class RelationDto implements Serializable {

	private static final long serialVersionUID = 5031043973759756542L;
	
	private Long relationId;
	private String relationName;
	private String relationCode;
	private String status;

}
