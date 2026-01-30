package com.relx.banking.customerservice.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Naveen.Sankhala
 * Sep 1, 2025
 */
@Data
@Entity
@Table(name="Customer")
@Builder @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class Customer extends AuditableEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Customer_Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerId;

	@Column(name = "CIF_NO", unique = true, nullable = false, insertable = false, updatable = false,length = 50)
	private String customerIdentificationNo;

	@Column(name="First_Name")
	private String firstName;

	@Column(name="Middle_Name")
	private String middleName;

	@Column(name="Last_Name")
	private String lastName;

	@Column(name="Date_Of_Created")
	private LocalDate dateOfCreated;

	@Column(name="Date_Of_Inactive")
	private LocalDate dateOfInactive;

	@Column(name="Status")
	private String status;

	@OneToMany(mappedBy="customer",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Address> address;
	
	@OneToOne(mappedBy="customer",fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private CustomerDetails customerDetails;
	
//	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
//	private List<JointAccountHolder> jointAccounts = new ArrayList<>();

}
