package com.relx.banking.authservice.jwt;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author Naveen.Sankhala
 * Sep 26, 2025
 */
@Data
@ApiModel(description="All details about login request. ")
public class JwtTokenRequest implements Serializable {

	@ApiModelProperty(notes="Enter Correct User Name", required = true)
	@NotNull(message = "is required")
	//@Pattern(regexp = "^[a-zA-Z0-9]", message="Enter Correct Service No.")
	private String username;

	@ApiModelProperty(notes="Password Can't be Blank.", required = true)
	@NotNull(message = "is required")
	private String password;
	
	@ApiModelProperty(notes="Branch Id Can't be Blank.", required = true)
	@NotNull(message = "is required")
	private long branchId;

	public JwtTokenRequest() {
		super();
	}

	public JwtTokenRequest(String username, String password, int branchId) {
		this.setUsername(username);
		this.setPassword(password);
		this.setBranchId(branchId);
	}
}
