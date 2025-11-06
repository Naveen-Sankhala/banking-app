package com.relx.banking.commondto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Naveen.Sankhala
 * Oct 31, 2025
 */
@Setter @Getter
public class OccupationDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long occupationId;
    private String hindiName;
    private String englishName;
    private String occupationCode;
    private BigDecimal marks;
    private BigDecimal guaranteedMarks;
    private String behaviour;
    private String salaryBehaviour;
    private String customerType;
    private String occupationGroup;
    private String status;
}
