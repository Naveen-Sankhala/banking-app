package com.relx.banking.authservice.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.v3.oas.annotations.Hidden;
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
import lombok.Data;

/**
 * @author Naveen.Sankhala
 * Sep 24, 2025
 */

@Hidden
@Entity
@Table(name="User_Roles")
@Data
public class UserRoles implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="Userrole_Id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long userRoleId;
	
//	@Column(name = "Role_Id")
//	private Long roleId;
	
	@OneToOne
	@JoinColumn(name="Role_Id",insertable=false,updatable=false)
	private MasRole masRole;
	
	@Column(name = "Branch_Id")
	private Long branchId;
	
//	@OneToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name="Branch_Id",insertable=false,updatable=false)
//	private Branch branch;
	
	@JsonBackReference 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="User_Id",nullable = false)	
	private Users users;
	
	@Column(name = "Status")
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
