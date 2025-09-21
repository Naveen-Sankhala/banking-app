package com.relx.banking.accountservice.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author Naveen.Sankhala
 * Sep 11, 2025
 */

@Entity
@Table(name="Joint_Account_Holder")
@Data
@Builder @AllArgsConstructor @NoArgsConstructor
public class JointAccountHolder implements Serializable {

	private static final long serialVersionUID = -851326996781655837L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Joint_Holder_Id")
    private Long jointHolderId;
    
    @JoinColumn(name = "Customer_Id", nullable = false)
    private Long customerId;

    @Builder.Default
    @Column(name = "Holder_Type",nullable = false, length = 20)
    private String holderType = "PRIMARY"; // PRIMARY, SECONDARY, NOMINEE

    @Column(name = "Added_Date")
    @CreationTimestamp
    private LocalDate addedDate;
    
    @ManyToOne(cascade = CascadeType.PERSIST ,fetch = FetchType.LAZY)
    @JoinColumn(name = "Account_Id",referencedColumnName ="Account_Id", nullable = false)
    @JsonIgnore
    private Account account;

}
