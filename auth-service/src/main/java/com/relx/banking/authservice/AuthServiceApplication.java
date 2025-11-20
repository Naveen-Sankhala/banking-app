package com.relx.banking.authservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AuthServiceApplication {
	private static final Logger logger = LoggerFactory.getLogger(AuthServiceApplication.class);
	
	public static void main(String[] args) {
		logger.info("Auth Service  Aplication Starting :::: ");
		SpringApplication.run(AuthServiceApplication.class, args);
	}

}
