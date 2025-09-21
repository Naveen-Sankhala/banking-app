package com.relx.banking.accountservice.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author Naveen.Sankhala
 * Sep 16, 2025
 */

@Configuration
@EnableJpaAuditing
//@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware") change if spring security use
public class JpaAuditableConfig {
	//if spring security use then remvoe this bean method
	
	@Bean
    public AuditorAware<Long> auditorProvider() {
        // Example: return currently logged-in user’s ID
        return () -> Optional.of(1L); 
    }
}
