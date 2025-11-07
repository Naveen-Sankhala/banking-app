package com.relx.banking.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * @author Naveen.Sankhala
 * Sep 25, 2025
 */
@Configuration
//@EnableWebFluxSecurity
public class GatewaySecurityConfig {
/*
	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		http
        .authorizeExchange(exchanges -> exchanges
            .pathMatchers("/bank/**").authenticated()
            .anyExchange().permitAll()
        )
        .oauth2ResourceServer(oauth2 -> oauth2.jwt());
    return http.build();
	}
*/
}
