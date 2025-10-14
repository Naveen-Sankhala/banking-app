package com.relx.banking.authservice.config;

import java.io.Serializable;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

/**
 * @author Naveen.Sankhala
 * Sep 30, 2025
 */
@Configuration
@Getter
public class AppConfig implements Serializable {
	
	private static final long serialVersionUID = 8935154567356995359L;
	
	@Value("${spring.cloud.config.profile}")
	private String enviornment;

	@Value("${corss.url}")
	private String corssUrl;

	@Value("${spring.security.oauth2.authorizationserver.issuer}")
	private String authorizationServerIssuer;
         
	@Value("${spring.security.oauth2.client-id}")
	private String clintId;
	
	@Value("${spring.security.oauth2.client-secret}")
	private String clientSecret;
	
	@Value("${spring.security.oauth2.redirect-uri}")
	private String redirectUri;
	
	@Value("${spring.security.oauth2.authorization-uri}")
	private String authorizationToken;
	
	@Value("${spring.security.oauth2.token-uri}")
	private String accessToken;
	
	@Value("${spring.security.oauth2.token-expiration}")
	private Instant tokenExpiration;
	
	@Value("${spring.security.oauth2.refreshToken-expiration}")
	private Instant refreshTokenExpiration;
	
//	@Value("${corss.url}")
//	private String corssUrl;
//	
//	@Value("${corss.url}")
//	private String corssUrl;
	

}
