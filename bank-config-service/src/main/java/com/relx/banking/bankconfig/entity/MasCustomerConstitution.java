package com.relx.banking.bankconfig.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * @author Naveen.Sankhala
 * Oct 13, 2025
 */
@Data
@Entity
@Table(name="MAS_CUSTOMER_CONSTITUTION")
public class MasCustomerConstitution implements Serializable {
	
	private static final long serialVersionUID = 3740680895579488138L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Con_Id")
    private Long conId;

    @Column(name = "Con_Hname")
    private String conHName;

    @Column(name = "Con_Ename")
    private String conEName;

    @Column(name = "Con_Code")
    private String conCode;

    @Column(name = "Con_Type")
    private String conType;
    
    @Column(name = "Con_Chqyn")
    private Character conChqyn;

    @Column(name = "Con_Npa")
    private Character conNpa;

    @Column(name = "Con_Risktype")
    private String conRiskType;

    @Column(name = "Con_Isteller")
    private BigDecimal conIsTeller;
    
    @Column(name = "Con_Iscii")
    private BigDecimal conIsCII;
    
    @Column(name = "Con_Lang1")
    private String conLang1;
    
    @Column(name = "Con_Lang2")
    private String conLang2;
    
    @Column(name = "Con_Lang3")
    private String conLang3;
    
    @Column(name = "Con_Lang4")
    private String conLang4;
    
    @Column(name = "Con_Lang5")
    private String conLang5;
    
    @Column(name = "Status")
    private String status;
    
    @Column(name="Created_By")
	private Long createdBy;

	@CreationTimestamp
	@Column(name="Created_Date")
	private LocalDateTime createdDate;
	
	@Column(name="Last_Chg_By")
	private Long lastChgBy;
	
	@UpdateTimestamp
	@Column(name="Last_Chg_Date")
	private LocalDateTime lastChgDate;

}
