package com.relx.banking.bankconfig.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
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
	private static final Logger logger = LoggerFactory.getLogger(RabbitConfig.class);

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

	@Bean
	public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
	}

	@Bean
	public ApplicationRunner declareQueues(AmqpAdmin amqpAdmin) {
		return args -> {
			amqpAdmin.declareQueue(new Queue("bank.config.queue", true));
			amqpAdmin.declareExchange(new TopicExchange("bank.config.exchange"));
			amqpAdmin.declareBinding(
					BindingBuilder.bind(new Queue("bank.config.queue", true))
					.to(new TopicExchange("bank.config.exchange"))
					.with("config.update")
					);
			logger.info("✅ Declared RabbitMQ queue and exchange before listeners start.");
		};
	}

	// --- Message Converter ---
	@Bean
	public MessageConverter jsonMessageConverter() {
		Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
		
		DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
		// ✅ your event package
		typeMapper.setTrustedPackages("com.relx.banking", "java.util", "java.lang"); 
		converter.setJavaTypeMapper(typeMapper);	    
		return converter;
	}

	// --- RabbitTemplate with JSON support ---
	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setMessageConverter(jsonMessageConverter);
		return template;
	}

	// --- Listener factory with JSON converter ---
	@Bean
	public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory,
			SimpleRabbitListenerContainerFactoryConfigurer configurer,MessageConverter jsonMessageConverter) {
		
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setMessageConverter(jsonMessageConverter);
		return factory;
	}
}
