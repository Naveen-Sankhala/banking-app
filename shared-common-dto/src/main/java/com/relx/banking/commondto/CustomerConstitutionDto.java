package com.relx.banking.commondto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Naveen.Sankhala
 * Oct 31, 2025
 */
@Getter @Setter
public class CustomerConstitutionDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long conId;
    private String conHName;
    private String conEName;
    private String conCode;
    private String conType;
    private Character conChqyn;
    private Character conNpa;
    private String conRiskType;
    private Character conIsTeller;
    private Character conIsCII;
    private String conLang1;
    private String conLang2;
    private String conLang3;
    private String conLang4;
    private String conLang5;
    private String status;

}
