package com.relx.banking.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author Naveen.Sankhala
 * Sep 24, 2025
 */
@EnableWebSecurity
public class DefaultSecurityConfig {
	
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests(authorizeRequests ->
				authorizeRequests.anyRequest().authenticated()
				)
				.formLogin(Customizer.withDefaults());
		return http.build();
	}

}
