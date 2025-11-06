package com.relx.banking.accountservice;

import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class AccountServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
	    Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
	    ((DefaultJackson2JavaTypeMapper) converter.getJavaTypeMapper())
	        .setTrustedPackages("*"); // temporary for debugging
	    return converter;
	}
}
