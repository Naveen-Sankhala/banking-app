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
@Table(name="BANK_TYPE")
public class BankType implements Serializable {

	private static final long serialVersionUID = 5516061836607650192L;
	
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "Type_Code")
	private String typeCode;
	
	@Column(name = "Description")
	private String description;
}
