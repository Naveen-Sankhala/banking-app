package com.relx.banking.customerservice.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.relx.banking.commondto.ConfigChangedEvent;

/**
 * @author Naveen.Sankhala
 * Sep 19, 2025
 */
@Component
public class ConfigListener {
	
	private final Map<String, Object> cachedConfigs = new ConcurrentHashMap<>();

    @RabbitListener(queues = "bank.config.queue")
    public void handleConfigUpdate(ConfigChangedEvent event) {
        cachedConfigs.put(event.getKey(), event.getValue());
        System.out.println("Config updated: " + event.getKey());
    }

    public Object getConfig(String key) {
        return cachedConfigs.get(key);
    }

}
