package com.relx.banking.bankconfig.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * @author Naveen.Sankhala
 * Oct 9, 2025
 */

@Data
@Entity
@Table(name="Mas_Status")
public class MasStatus implements Serializable {

	private static final long serialVersionUID = -1831861668393263023L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Status_Id")
	private Long statusId;
	
	@Column(name = "Status_Name")
	private String statusName;
	
	@Column(name = "Status_Code")
	private String statusCode;
	
	@Column(name = "Status_Table")
	private String statusTable;

}
