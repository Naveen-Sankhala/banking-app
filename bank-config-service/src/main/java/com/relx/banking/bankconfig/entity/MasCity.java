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
@Table(name="Mas_City")
public class MasCity implements Serializable {

	private static final long serialVersionUID = -5086034647618142440L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "City_Id")
	private Long cityId;
	
	@Column(name = "City_Name")
	private String cityName;
	
	@Column(name = "City_Code")
	private String cityCode;
	
	@Column(name = "State_Id")
	private Long stateId;

}
