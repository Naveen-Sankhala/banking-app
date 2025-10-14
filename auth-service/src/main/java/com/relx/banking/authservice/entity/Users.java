package com.relx.banking.authservice.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * @author Naveen.Sankhala
 * Sep 24, 2025
 */
//@ApiIgnore
@Entity
@Table(name="Users")
@Data
public class Users implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="User_Id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long userId;
	
	@Column(name ="Customer_Id")
	private Long customerId;
	
	@Column(name="User_Name", nullable = false, unique = true, length = 20)
	private String username;
	
	@Column(name="Login_Name", nullable = false, length = 50)
	private String loginName;
	
	@Column(name="Email_Id")
	private String emailId;
	
	@Column(name = "Status", nullable = false, length = 1)
	private String status;
	
	@Column(name ="created_by")
	private Long createdBy;
	
	@CreationTimestamp
	@Column(name="created_date")
	private LocalDateTime createdDate;

	@Column(name="last_chg_by")
	private Long lastChgBy;
	
	@UpdateTimestamp
	@Column(name="last_chg_date")
	private LocalDateTime lastChgDate;

	@JsonIgnore
	@JsonManagedReference
	@OneToMany(mappedBy="users",cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
	private Set<UserRoles> userRoleList;
//	
//	@JsonIgnore
//	@OneToMany(mappedBy="users",cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
//	private Set<UserHospitals> userHospitals =new HashSet<>();

	public Users() {
		
	}

}
