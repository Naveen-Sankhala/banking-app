package com.relx.banking.customerservice.config;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.relx.banking.commondto.BankConfigurationDto;
import com.relx.banking.customerservice.client.BankConfigApi;
import com.relx.banking.customerservice.util.ObjectMapperUtils;
import com.relx.banking.eventchanged.ConfigChangedEvent;


/**
 * @author Naveen.Sankhala
 * Sep 19, 2025
 */
@Component
public class ConfigListener {
	private static final Logger logger = LoggerFactory.getLogger(ConfigListener.class);
	private final Map<String, Object> cachedConfigs = new ConcurrentHashMap<>();

	private final BankConfigApi bankConfigApi;
	private final BankConfigurationHolder configHolder;

	@Autowired
	public ConfigListener(BankConfigApi bankConfigApi,BankConfigurationHolder configHolder) {
		this.bankConfigApi = bankConfigApi;
		this.configHolder = configHolder;
	}

	@RabbitListener(queues = "bank.config.queue")
	public void handleConfigUpdate(ConfigChangedEvent event) {
		cachedConfigs.put(event.getKey(), event.getValue());
		logger.info("♻️ Config updated from MQ: {}", event.getKey());

		// When BankConfig key arrives, trigger initialization
		if ("BankConfig".equals(event.getKey())) {
			initializeBankConfiguration();
		}

	}

	//@PostConstruct
	@EventListener(ContextRefreshedEvent.class)
	public void onApplicationReady() {
		try {
			// Wait briefly to give MQ a chance to deliver configs
            int waitAttempts = 3;
            int waitIntervalMillis = 1000;

            for (int i = 0; i < waitAttempts; i++) {
            	if (!cachedConfigs.isEmpty() &&  cachedConfigs.size() > 0) {
            		logger.info("✅ Configs already received from MQ during startup. Skipping API call.");
            		return;
            	}
            	logger.info("⏳ Waiting {}s for MQ configs to arrive... (attempt {}/{})",
            			waitIntervalMillis / 1000, i + 1, waitAttempts);
            	Thread.sleep(waitIntervalMillis);
            }
			
			
			if (cachedConfigs.isEmpty() &&  cachedConfigs.size() == 0) {
				//logger.warn("⚠️ No config found from MQ yet. Falling back to Config API...");
				logger.info("🟡 Loading initial configs from Config API...");
				Map<String, Object> configs = bankConfigApi.getAllCommonConfiguration();
				if (configs != null && !configs.isEmpty()) {
					cachedConfigs.putAll(configs);
					logger.info("✅ Loaded {} configs from API.", configs.size());

					if (configs.containsKey("BankConfig")) {
						initializeBankConfiguration();
					}
				} else {
					logger.warn("⚠️ No configs found in Config API.");
				}
			}else {
				logger.info("✅ Skipping API call — configs already received from MQ.");
			}
		}catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("🛑 Startup wait interrupted", e);
        } catch (Exception e) {
			logger.error("❌ Failed to fetch initial configuration", e);
		}
	}


	public Object getConfig(String key) {
		Object value = cachedConfigs.get(key);
		if (value == null) {
			logger.warn("⚠️ Config '{}' not found in cache. Fetching from API...", key);
			try {
				Object apiValue = bankConfigApi.getConfigByKey(key);
				if (apiValue != null) {
					cachedConfigs.put(key, apiValue);
					logger.info("✅ Loaded config '{}' from API and cached it.", key);
					return apiValue;
				} else {
					logger.warn("⚠️ Config '{}' not found in API either.", key);
				}
			} catch (Exception e) {
				logger.error("❌ Failed to fetch config '{}' from API", key, e);
			}
		}
		return value;
	}

	public void initializeBankConfiguration() {
		Object bankConfigObj = cachedConfigs.get("BankConfig");

		if (bankConfigObj == null) {
			logger.warn("⚠️ No BankConfig entry found in cachedConfigs!");
			return;
		}

		BankConfigurationDto bankConfig = null;

		if (bankConfigObj instanceof BankConfigurationDto) {
			bankConfig = (BankConfigurationDto) bankConfigObj;
		} else if (bankConfigObj instanceof Map) {
			try {
				bankConfig = ObjectMapperUtils.convertValue(bankConfigObj, BankConfigurationDto.class);
				logger.info("🔄 Converted cached BankConfig LinkedHashMap to BankConfigurationDto.");
			} catch (IllegalArgumentException e) {
				logger.error("❌ Failed to convert cached BankConfig map to DTO: {}", e.getMessage(), e);
				return;
			}
		} else {
			logger.warn("⚠️ Unsupported type for BankConfig in cachedConfigs: {}", bankConfigObj.getClass().getName());
			return;
		}

		// AtomicReference in configHolder handles thread safety internally
		if (configHolder.isInitialized()) {
			configHolder.updateConfig(bankConfig);
			logger.info("♻️ BankConfiguration auto-refreshed from cachedConfigs (atomic).");
		} else {
			configHolder.setConfig(bankConfig);
			logger.info("✅ BankConfiguration initialized from cachedConfigs for the first time (atomic).");
		}
	}



	public Map<String, Object> getAllCachedConfigs() {
		return Collections.unmodifiableMap(cachedConfigs);
	}
}
