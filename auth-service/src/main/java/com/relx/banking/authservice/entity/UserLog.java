package com.relx.banking.authservice.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * @author Naveen.Sankhala
 * Sep 24, 2025
 */
//@ApiIgnore
@Entity
@Table(name="USER_LOG")
@Data
public class UserLog implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@Id
	@Column(name="UserLog_Id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long userLogId;
	
	@JsonIgnore
    @NotNull
    @Column(name="User_Id", length = 10)
    private Long userId;
    
    @NotNull
    @Column(name="Ip_Address", length = 15)
    private String ipAddress;
    
    @NotNull
    @Column(name="isLoggedIn")
    private boolean isLoggedIn;
    
    @JsonIgnore
    @Lob
    @Column(name="Refresh_Token")
    private String refreshToken;
    
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @Column(name="Logged_InTime")
	private LocalDateTime loggedInTime;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    @Column(name="Logged_OutTime")
	private LocalDateTime loggedOutTime;


}
