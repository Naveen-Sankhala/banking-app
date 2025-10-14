package com.relx.banking.bankconfig.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * @author Naveen.Sankhala
 * Oct 9, 2025
 */
@Data
@Entity
@Table(name="Mas_Currency")
public class MasCurrency implements Serializable {

	private static final long serialVersionUID = 8792369375810308246L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Currency_Id")
	private Long currencyId;
	
	@Column(name = "Currency_Name")
	private String currencyName;
	
	@Column(name = "Currency_Code")
	private String currencyCode;
	
	@Column(name = "Country_Id")
	private Long countryId;


}
