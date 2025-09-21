package com.relx.banking.accountservice.config;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

//For Authentication and SecurityContextHolder
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;

//For UserDetails
//import org.springframework.security.core.userdetails.UserDetails;


/**
 * @author Naveen.Sankhala
 * Sep 17, 2025
 */
/*
@Component
public class SpringSecurityAuditorAware implements AuditorAware<Long> {
	
	@Override
    public Optional<Long> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        Object principal = authentication.getPrincipal();

        // Case 1: Your UserDetails holds a Long ID
        if (principal instanceof CustomUserDetails) {
            return Optional.of(((CustomUserDetails) principal).getId()); 
        }

        // Case 2: fallback to username hashCode (not recommended for production)
        return Optional.empty();
    }
}
*/