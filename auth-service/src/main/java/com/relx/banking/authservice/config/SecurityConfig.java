package com.relx.banking.authservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.relx.banking.authservice.service.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

/**
 * @author Naveen.Sankhala
 * Sep 24, 2025
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final CustomUserDetailsService userDetailsService; // implement UserDetailsService using iAuthService
	private final PasswordEncoder passwordEncoder;

	/**
	 *	Main app security filter chain — applies to everything except OAuth2 endpoints.
	 */
	@Bean
	@Order(2)
	public SecurityFilterChain appSecurityFilterChain(HttpSecurity http) throws Exception {
		http
		.securityMatcher("/auth/**", "/api/**", "/h2-console/**")
		.csrf(csrf -> csrf.disable())
		.authorizeHttpRequests(auth -> auth
				.requestMatchers("/auth/**", "/h2-console/**").permitAll()
				.anyRequest().authenticated()
				)
		.headers(headers -> headers.frameOptions().disable());

		return http.build();
	}
	
	/**
	 *	Authentication Manager for login
	 */
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder);

		AuthenticationManagerBuilder auth = http.getSharedObject(AuthenticationManagerBuilder.class);
		auth.authenticationProvider(authProvider);
		return auth.build();
	}

}
