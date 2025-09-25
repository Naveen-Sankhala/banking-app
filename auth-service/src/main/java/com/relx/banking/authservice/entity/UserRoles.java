package com.relx.banking.authservice.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

/**
 * @author Naveen.Sankhala
 * Sep 24, 2025
 */

//@ApiIgnore
@Entity
@Table(name="USER_ROLES")
public class UserRoles implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="USERROLE_ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long userRoleId;
	
	@Column(name = "ROLE_ID")
	private Long roleId;
	
//	@OneToOne
//	@JoinColumn(name="ROLE_ID",insertable=false,updatable=false)
//	private MasRole masRole;
	
	@Column(name = "HOSPITAL_ID")
	private Long hospitalId;
	
//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name="HOSPITAL_ID",insertable=false,updatable=false)
//	private MasHospital masHospital;
	
	//@JsonBackReference 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="USER_ID",nullable = false)	
	private Users users;
	
	@Column(name = "Status")
	private String status;
	
	@Column(name = "CREATED_BY")
	private Long createdBy;

	@CreationTimestamp
	@Column(name="Created_Date")
	private LocalDateTime createdDate;

	@Column(name = "LAST_CHG_BY")
	private Long lastChangedBy;

	@UpdateTimestamp
	@Column(name="last_chg_date")
	private LocalDateTime lastChgDate;


}
