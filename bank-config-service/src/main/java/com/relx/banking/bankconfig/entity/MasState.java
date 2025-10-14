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
@Table(name="Mas_State")
public class MasState implements Serializable {

	private static final long serialVersionUID = 3379615905864709296L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "State_Id")
	private Long stateId;

	@Column(name = "State_Name")
	private String stateName;

	@Column(name = "State_Code")
	private String stateCode;

	@Column(name = "Country_Id")
	private Long countryId;

}
