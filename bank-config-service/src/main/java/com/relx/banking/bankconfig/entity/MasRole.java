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
 * Oct 9, 2025
 */

@Data
@Entity
@Table(name="Mas_Role")
public class MasRole implements Serializable {

	private static final long serialVersionUID = -2059659263361087606L;
	
	@Id
	@Column(name = "Role_Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long roleId;
	
	@Column(name="Role_Code")
	private String roleCode;
	
	@Column(name="Role_Name")
	private String roleName;
	
	@Column(name="Role_Category")
	private String roleCategory;
	
	@Column(name="Description")
	private String description;
	
	@Column(name="Status")
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
