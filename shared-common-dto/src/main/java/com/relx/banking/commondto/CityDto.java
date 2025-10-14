package com.relx.banking.commondto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Naveen.Sankhala
 * Oct 10, 2025
 */

@Getter @Setter
public class CityDto implements Serializable {

	private static final long serialVersionUID = 7962959802450004997L;

	private Long cityId;
	private String cityName;
	private String cityCode;
	private Long stateId;

}
