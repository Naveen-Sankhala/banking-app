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
@Table(name="MAS_RELATION")
public class MasRelation implements Serializable {

	private static final long serialVersionUID = -6462500127468511676L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Relation_Id")
	private Long relationId;
	
	@Column(name = "Relation_Name")
	private String relationName;
	
	@Column(name = "Relation_Code")
	private String relationCode;
	
	@Column(name = "Status")
	private String status;

}
