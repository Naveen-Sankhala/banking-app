package com.relx.banking.usermanagement.config;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.relx.banking.commonsecurity.ClaimsData;

/**
 * @author Naveen.Sankhala
 * Jan 9, 2026
 */
@Component
public class AuthenticationFacade {
	
	public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

	public Long getUserId() {
		ClaimsData claimsData = getClaims();
		return claimsData != null ? claimsData.getUserId() : null;
	}
	
	public String getUserName() {
		ClaimsData claimsData = getClaims();
		return claimsData.getUserName();
	}

	public String getLoginName() {
		ClaimsData claimsData = getClaims();
		return claimsData.getLoginName();
	}

	public Long getBranchId() {
		ClaimsData claimsData = getClaims();
		return claimsData.getBranchId();
	}
	
	public String getBranchName() {
		ClaimsData claimsData = getClaims();
		return claimsData.getBranchName();
	}

	public List<String> getUserRoles() {
		ClaimsData claimsData = getClaims();
		return claimsData.getRoles();
	}
	
	public String getBranchType() {
		ClaimsData claimsData = getClaims();
		return claimsData.getBranchType();
	}
	
	public ClaimsData getClaims() {
	    Authentication auth = getAuthentication();

	    if (auth == null || !auth.isAuthenticated()
	        || !(auth.getPrincipal() instanceof ClaimsData)) {
	        return null;
	    }

	    return (ClaimsData) auth.getPrincipal();
	}

}
