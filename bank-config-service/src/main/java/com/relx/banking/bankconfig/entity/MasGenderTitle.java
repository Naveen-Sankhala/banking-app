package com.relx.banking.bankconfig.entity;

import java.io.Serializable;
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
 * Oct 8, 2025
 */

@Data
@Entity
@Table(name="MAS_GENDER_TITLE")
public class MasGenderTitle implements Serializable {

	private static final long serialVersionUID = 7651635111677083110L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "Gender_Code", length = 1, nullable = false)
	private String genderCode;

	@Column(name = "Gender_Name", length = 20, nullable = false)
	private String genderName;

	@Column(name = "Title_Code", length = 10, nullable = false)
	private String titleCode;

	@Column(name = "Title_Name", length = 50, nullable = false)
	private String titleName;

	@Column(name = "Status", length = 1)
	private String status = "Y";

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
