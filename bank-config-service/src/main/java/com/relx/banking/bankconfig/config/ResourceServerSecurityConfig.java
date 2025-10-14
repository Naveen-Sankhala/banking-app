package com.relx.banking.bankconfig.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
//import org.springframework.security.web.SecurityFilterChain;

/**
 * @author Naveen.Sankhala
 * Sep 25, 2025
 */

@Configuration
//@EnableWebSecurity
public class ResourceServerSecurityConfig {
	
//	@Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//            .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/bank/accounts/**").hasAuthority("SCOPE_bank.read")
//                .anyRequest().authenticated()
//            )
//            .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
//        //  .oauth2ResourceServer(oauth2 -> oauth2.jwt());
//        return http.build();
//    }

}
