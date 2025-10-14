package com.relx.banking.authservice.jwt;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

/**
 * @author Naveen.Sankhala
 * Sep 26, 2025
 */
@Getter
public class JwtTokenResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final String token;
	private final String refreshToken;
	private final String cifNo;
	private final String branchlName;
	private final String userName;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private final LocalDateTime lastLoggedInTime;
	
	
	public JwtTokenResponse(String token, String refreshToken, String serviceNumber, String hospitalName, String userName, LocalDateTime lastLoggedInTime) {
		this.token = token;
		this.refreshToken = refreshToken;
		this.cifNo = serviceNumber;
		this.branchlName = hospitalName;
		this.userName = userName;
		this.lastLoggedInTime = lastLoggedInTime;
	}


}
