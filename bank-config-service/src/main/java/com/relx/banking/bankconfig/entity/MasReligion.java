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
@Table(name="MAS_RELIGION")
public class MasReligion implements Serializable {

	private static final long serialVersionUID = 5720740717841603190L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Religion_Id")
	private Long religionId;

	@Column(name = "Rlgn_Ename")
	private String rlgnEname;

	@Column(name = "Rlgn_Hname")
	private String rlgnHname;

	@Column(name = "Rlgn_Code")
	private String rlgnCode;

	@Column(name = "Status")
	private BigDecimal status;

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
