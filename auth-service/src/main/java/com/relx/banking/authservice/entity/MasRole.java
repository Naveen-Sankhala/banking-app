package com.relx.banking.authservice.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * @author Naveen.Sankhala
 * Sep 30, 2025
 */
@Hidden
@Entity
@Table(name="Mas_Role")
@Data
public class MasRole implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="Role_Id")
	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long roleId;

	@Column(name="Role_Code")
	private String roleCode;
	
	@Column(name="Role_Name")
	private String roleName;
	
	@Column(name="Role_Category")
	private String roleCategory;
	
	@Column(name="Description")
	private String description;

	@JsonIgnore
	@Column(name = "Status")
	private String status;

	@JsonIgnore
	@Column(name ="created_by")
	private Long createdBy;
	
	@JsonIgnore
	@CreationTimestamp
	@Column(name="created_date")
	private LocalDateTime createdDate;

	@JsonIgnore
	@Column(name="last_chg_by")
	private Long lastChgBy;
	
	@JsonIgnore
	@UpdateTimestamp
	@Column(name="last_chg_date")
	private LocalDateTime lastChgDate;

}
