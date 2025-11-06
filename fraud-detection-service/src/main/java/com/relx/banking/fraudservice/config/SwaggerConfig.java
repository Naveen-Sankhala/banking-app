package com.relx.banking.fraudservice.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.info.Contact;

/**
 * @author Naveen.Sankhala
 * Nov 06, 2025
 */
@Configuration
public class SwaggerConfig {

	public static final Contact DEFAULT_CONTACT= new Contact();
	
	@Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
        		.group("public")
                .pathsToMatch("/**")
                .build();
    }
}
