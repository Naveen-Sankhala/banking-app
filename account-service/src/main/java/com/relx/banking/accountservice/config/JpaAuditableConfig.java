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
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
public class JpaAuditableConfig {

	@Bean
	public AuditorAware<Long> springSecurityAuditorAware(
			AuthenticationFacade authenticationFacade) {

		return () -> Optional.ofNullable(authenticationFacade.getUserId());
	}
}
