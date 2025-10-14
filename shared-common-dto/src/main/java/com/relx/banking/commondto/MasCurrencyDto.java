package com.relx.banking.commondto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Naveen.Sankhala
 * Oct 9, 2025
 */
@Setter @Getter
public class MasCurrencyDto implements Serializable {

	private static final long serialVersionUID = 3086771470129778571L;

	private Long currencyId;
	private String currencyName;
	private String currencyCode;
	private Long countryId;

}
