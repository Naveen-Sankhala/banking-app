package com.relx.banking.bankconfig.config;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.relx.banking.bankconfig.entity.BankConfiguration;
import com.relx.banking.bankconfig.service.IConfigService;
import com.relx.banking.bankconfig.service.IMasterService;
import com.relx.banking.commondto.BankConfigurationDto;
import com.relx.banking.commondto.CastDto;
import com.relx.banking.commondto.CustomerConstitutionDto;
import com.relx.banking.commondto.GenderTitleDto;
import com.relx.banking.commondto.MasCurrencyDto;
import com.relx.banking.commondto.OccupationDto;
import com.relx.banking.commondto.RelationDto;
import com.relx.banking.commondto.ReligionDto;
import com.relx.banking.eventchanged.ConfigChangedEvent;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

/**
 * @author Naveen.Sankhala
 * Sep 19, 2025
 */
@Component
@RequiredArgsConstructor
public class ConfigInitializer {
	private static final Logger logger = LoggerFactory.getLogger(ConfigInitializer.class);
	
	private final Map<String, Object> configMap = new ConcurrentHashMap<>();
	
	private final IConfigService iConfigService;
	
	private final IMasterService iMasterService;
	
	private final RabbitTemplate rabbitTemplate;
	
	
	@PostConstruct
	public void init() {
	    logger.info("🏦 Initializing Bank Configuration...");
	    loadConfigs();
	    rePublishAllConfigs(); // 🔁 re-send all configs on startup
	}
	
	public void loadConfigs() {
		BankConfigurationDto config = iConfigService.getBankConfiguration(); 
		if(config !=null) {
			configMap.put("BankConfig", config);
		}
		
		List<MasCurrencyDto> masCurrency = iMasterService.getMasCurrency();
		List<GenderTitleDto> masGenderTitle = iMasterService.getGenderTitle();
		List<RelationDto> masRelation = iMasterService.getMasRelation();
		List<OccupationDto> masOccupation = iMasterService.getMasOccupation();
		List<ReligionDto> masReligion = iMasterService.getMasReligion();
		List<CastDto> masCast = iMasterService.getMasCast();
		List<CustomerConstitutionDto> masCustomerConst = iMasterService.getMasCustomerConstitution();

		configMap.put("MasCurrency", masCurrency);
		configMap.put("MasGenderTitle", masGenderTitle);
		configMap.put("MasRelation", masRelation);
		configMap.put("MasOccupation", masOccupation);
		configMap.put("MasReligion", masReligion);
		configMap.put("MasCast", masCast);
		configMap.put("MasCustomerConst", masCustomerConst);

			// Load Bank Date (daily changing)
			// Load Account Categories
	}

	public Object getConfig(String key) {
        return configMap.get(key);
    }

    public Map<String, Object> getAllCommonConfiguration() {
        return Collections.unmodifiableMap(configMap);
    }
    
    private void publishConfig(String key, Object value) {
        rabbitTemplate.convertAndSend(
                "bank.config.exchange", "config.update",
                new ConfigChangedEvent(key, value)
            );
        logger.info("🔁 Published config change: {}", key);
    }
    
    private void rePublishAllConfigs() {
        logger.info("🔁 Republishing all configs to RabbitMQ...");
        configMap.forEach(this::publishConfig);
       // logger.info("✅ All configs re-published successfully!");
    }
}
