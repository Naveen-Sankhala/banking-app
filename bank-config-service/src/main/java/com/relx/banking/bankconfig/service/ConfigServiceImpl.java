package com.relx.banking.bankconfig.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.relx.banking.bankconfig.dao.IConfigDao;
import com.relx.banking.bankconfig.dto.ConfigChangedEvent;
import com.relx.banking.bankconfig.entity.BankConfiguration;
import com.relx.banking.bankconfig.util.ObjectMapperUtils;
import com.relx.banking.commondto.BankConfigurationDto;

import lombok.RequiredArgsConstructor;

/**
 * @author Naveen.Sankhala
 * Sep 18, 2025
 */
@Service
@RequiredArgsConstructor
public class ConfigServiceImpl implements IConfigService {
	private static final Logger logger = LoggerFactory.getLogger(ConfigServiceImpl.class);
	
	private final IConfigDao iConfigDao;
	private final RabbitTemplate rabbitTemplate;
	
		
	@Override
	public BankConfigurationDto getBankConfiguration() {
		BankConfiguration config =  iConfigDao.getBankConfiguration();
		BankConfigurationDto configDto = ObjectMapperUtils.map(config, BankConfigurationDto.class);
		return configDto;
	}
	
	public BankConfiguration updateConfig(BankConfiguration config) {
        BankConfiguration saved = iConfigDao.saveBankConfiguration(config);
        // Publish event
        rabbitTemplate.convertAndSend(
            "bank.config.exchange", "config.update",
            new ConfigChangedEvent("bankConfig", saved)
        );
        return saved;
    }
}
