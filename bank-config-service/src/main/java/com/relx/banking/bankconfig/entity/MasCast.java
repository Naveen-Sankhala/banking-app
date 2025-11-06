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
@Table(name="MAS_CAST")
public class MasCast implements Serializable {

	private static final long serialVersionUID = -5815594137479138947L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Cast_Id")
	private Long castId;

	@Column(name = "Cast_Ename")
	private String castEName;

	@Column(name = "Cast_Hname")
	private String castHName;

	@Column(name = "Cast_Code")
	private String castCode;

	@Column(name = "Cast_Type")
	private String castType;

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
