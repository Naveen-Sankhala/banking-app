/**
 * 
 */
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
@Table(name="Mas_Country")
public class MasCountry implements Serializable {

	private static final long serialVersionUID = 7624319124296100770L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Country_Id")
	private Long countryId;
	
	@Column(name = "Country_Name")
	private String countryName;
	
	@Column(name = "Country_Code")
	private String countryCode;
	
}
