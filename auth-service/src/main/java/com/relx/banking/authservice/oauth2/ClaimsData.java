package com.relx.banking.authservice.oauth2;

import java.io.Serializable;
import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * @author Naveen.Sankhala
 * Sep 29, 2025
 */
@Data @Builder
public class ClaimsData implements Serializable {

	private static final long serialVersionUID = 2701621693174006672L;
	
	private Long UserId;
	private String UserName;
	private String LoginName;
	private Long BranchId;
	private String BranchName;
	private String BranchType;
	private List<String> Roles;

}
