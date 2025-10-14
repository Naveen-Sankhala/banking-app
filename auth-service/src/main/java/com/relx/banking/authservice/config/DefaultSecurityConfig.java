package com.relx.banking.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;

/**
 * @author Naveen.Sankhala
 * Sep 24, 2025
 */
//@Configuration
//@EnableWebSecurity
public class DefaultSecurityConfig {
	
//	@Bean
//	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception{
//		http.authorizeHttpRequests(authorizeRequests ->
//				authorizeRequests.anyRequest().authenticated()
//				)
//				.formLogin(Customizer.withDefaults());
//		return http.build();
//	}

//	@Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http,
//                                                       UserDetailsService userDetailsService,
//                                                       PasswordEncoder passwordEncoder) throws Exception {
//        return http.getSharedObject(AuthenticationManagerBuilder.class)
//                .userDetailsService(userDetailsService)
//                .passwordEncoder(passwordEncoder)
//                .and()
//                .build();
//    }
}
