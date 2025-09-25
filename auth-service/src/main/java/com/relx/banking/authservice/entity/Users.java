package com.relx.banking.authservice.entity;

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
 * Sep 24, 2025
 */
//@ApiIgnore
@Entity
@Table(name="Users")
@Data
public class Users implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="user_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long userId;
	
	@Column(name="login_name", nullable = false, length = 50)
	private String loginName;
	
	@Column(name="user_name", nullable = false, unique = true, length = 20)
	private String username;
	
	@Column(name="email_address")
	private String emailAddress;
	
	@Column(name ="PATIENT_ID")
	private Long patientId;
	
	@Column(name ="created_by")
	private Long createdBy;
	
	@CreationTimestamp
	@Column(name="created_date")
	private LocalDateTime createdDate;

	@Column(name = "Status", nullable = false, length = 1)
	private String status;
	
	@Column(name="last_chg_by")
	private Long lastChgBy;
	
	@UpdateTimestamp
	@Column(name="last_chg_date")
	private LocalDateTime lastChgDate;

//	@JsonIgnore
//	//@JsonManagedReference
//	@OneToMany(mappedBy="users",cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
//	private Set<UserRoles> userRoleList;
//	
//	@JsonIgnore
//	@OneToMany(mappedBy="users",cascade = CascadeType.ALL ,fetch = FetchType.LAZY)
//	private Set<UserHospitals> userHospitals =new HashSet<>();

	public Users() {
		
	}

}
