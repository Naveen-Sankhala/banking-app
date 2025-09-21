package com.relx.banking.bankconfig.config;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.relx.banking.bankconfig.entity.BankConfiguration;
import com.relx.banking.bankconfig.service.IConfigService;
import com.relx.banking.commondto.BankConfigurationDto;

import jakarta.annotation.PostConstruct;

/**
 * @author Naveen.Sankhala
 * Sep 19, 2025
 */
@Component
public class ConfigInitializer {
	private static final Logger logger = LoggerFactory.getLogger(ConfigInitializer.class);
	
	private final Map<String, Object> configMap = new ConcurrentHashMap<>();
	
	private final IConfigService iConfigService;
	
	ConfigInitializer(IConfigService iConfigService){
		this.iConfigService = iConfigService;
	}
	
	@PostConstruct
	public void init() {

		// Load Bank Configuration (single record)

		BankConfigurationDto config = iConfigService.getBankConfiguration(); 
		
		if(config !=null)
			configMap.put("bankConfig", config);


			// Load Bank Date (daily changing)
			// Load Account Categories
	}

	public Object getConfig(String key) {
        return configMap.get(key);
    }

    public Map<String, Object> getAllCommonConfiguration() {
        return Collections.unmodifiableMap(configMap);
    }

    public void updateConfig(String key, Object value) {
        configMap.put(key, value);
    }
}
