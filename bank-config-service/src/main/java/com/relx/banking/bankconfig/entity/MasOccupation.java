package com.relx.banking.bankconfig.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

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
@Table(name = "MAS_OCCUPATION")
public class MasOccupation implements Serializable {
	
	private static final long serialVersionUID = -98337275944576945L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Ocp_Id")
    private Long occupationId;

    @Column(name = "Ocp_Hname", length = 100)
    private String hindiName;

    @Column(name = "Ocp_Ename", length = 100)
    private String englishName;

    @Column(name = "Ocp_Code", length = 20, unique = true)
    private String occupationCode;

    @Column(name = "Marks", precision = 10, scale = 2)
    private BigDecimal marks;

    @Column(name = "Guaranteed_Marks", precision = 10, scale = 2)
    private BigDecimal guaranteedMarks;

    @Column(name = "Behaviour", length = 100)
    private String behaviour;

    @Column(name = "Salary_Behaviour", length = 100)
    private String salaryBehaviour;

    @Column(name = "Customer_Type", length = 50)
    private String customerType;

    @Column(name = "Occupation_Group", length = 50)
    private String occupationGroup;

    @Column(name = "Status", length = 1)
    private String status = "Y";

    // Optional: relationships to USERS table (foreign keys)
   
    @Column(name = "Created_By")
    private Long createdBy;

    @CreationTimestamp
    @Column(name = "Created_Date")
    private LocalDateTime createdDate;

    @Column(name = "Last_Chg_By")
    private Long lastChangedBy;

    @UpdateTimestamp
    @Column(name = "Last_Chg_Date")
    private LocalDateTime lastChangedDate;

}
