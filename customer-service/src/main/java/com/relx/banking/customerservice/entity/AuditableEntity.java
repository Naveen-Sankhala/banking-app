package com.relx.banking.customerservice.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Naveen.Sankhala
 * Sep 16, 2025
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter
public abstract class AuditableEntity implements Serializable {

	private static final long serialVersionUID = 6357415791264907808L;
	
	@CreatedBy
	@Column(name="Created_By" , updatable = false)
	private Long createdBy;
    
	@CreationTimestamp
    @Column(name="Created_Date" , updatable = false)
    private LocalDateTime createdDate;
    
    @LastModifiedBy
    @Column(name="Last_Chg_By")
	private Long lastChgBy;
    
    @UpdateTimestamp
    @Column(name="Last_Chg_Date")
    private LocalDateTime lastChgDate;

}
