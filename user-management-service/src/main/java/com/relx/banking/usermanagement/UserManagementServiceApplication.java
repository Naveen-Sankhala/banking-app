package com.relx.banking.usermanagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class UserManagementServiceApplication {
	private static final Logger logger = LoggerFactory.getLogger(UserManagementServiceApplication.class);

	public static void main(String[] args) {
		logger.info("User-Management Service  Aplication Starting :::: ");
		SpringApplication.run(UserManagementServiceApplication.class, args);
	}

}
