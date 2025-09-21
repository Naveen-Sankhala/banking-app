package com.relx.banking.bankconfig.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

/**
 * @author Naveen.Sankhala
 * Sep 18, 2025
 */

@Entity
@Table(name="BANK_CONFIGURATION")
@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class BankConfiguration implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Config_Id")
    private Long configId;

    @Column(name = "Bank_Id", nullable = false)
    private Long bankId;

    @Column(name = "English_Name", nullable = false, length = 100)
    private String englishName;

    @Column(name = "Hindi_Name", length = 100)
    private String hindiName;

    @Column(name = "Country_Id")
    private Long countryId;

    @Column(name = "Currency_Id")
    private Long currencyId;

    @Builder.Default
    @Column(name = "Allow_Multiple_Currency", length = 1)
    private String allowMultipleCurrency = "N";

    @Column(name = "Bank_Date", nullable = false)
    private LocalDate bankDate;

    @Builder.Default
    @Column(name = "Day_End_Process", length = 1)
    private String dayEndProcess = "N";

    @Column(name = "Financial_Year_Start", nullable = false)
    private LocalDate financialYearStart;

    @Column(name = "Financial_Year_End")
    private LocalDate financialYearEnd;

    @Builder.Default
    @Column(name = "Audit_Trail_Required", length = 1)
    private String auditTrailRequired = "N";

    @Column(name = "Audit_Option", length = 20)
    private String auditOption;

    @Column(name = "Audit_Number")
    private Long auditNumber;

    @Builder.Default
    @Column(name = "Single_Mode_Enabled", length = 1)
    private String singleModeEnabled = "N";

    @Column(name = "Ecs_Bank_Code", length = 20)
    private String ecsBankCode;

    @Builder.Default
    @Column(name = "Same_Loan_Account_Yn", length = 1)
    private String sameLoanAccountYn = "N";

    @Column(name = "Client_Code", length = 20)
    private String clientCode;

    @Column(name = "Created_Date", updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdDate;

}
