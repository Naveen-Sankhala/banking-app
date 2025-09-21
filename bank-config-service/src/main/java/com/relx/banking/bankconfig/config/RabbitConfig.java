package com.relx.banking.bankconfig.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Naveen.Sankhala
 * Sep 19, 2025
 */
@Configuration
public class RabbitConfig {

	@Bean
	public TopicExchange configExchange() {
		return new TopicExchange("bank.config.exchange");
	}

	@Bean
	public Queue configQueue() {
		return new Queue("bank.config.queue", true);
	}

	@Bean
	public Binding binding(Queue configQueue, TopicExchange configExchange) {
		return BindingBuilder.bind(configQueue).to(configExchange).with("config.update");
	}
}
