package com.relx.banking.bankconfig.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.relx.banking.bankconfig.dao.IConfigDao;
import com.relx.banking.bankconfig.entity.BankConfiguration;
import com.relx.banking.bankconfig.entity.Branch;
import com.relx.banking.bankconfig.util.ObjectMapperUtils;
import com.relx.banking.commondto.BankConfigurationDto;
import com.relx.banking.commonrecord.BranchDetailsRecord;
import com.relx.banking.eventchanged.ConfigChangedEvent;

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
            new ConfigChangedEvent("BankConfig", saved)
        );
        logger.info("✅ Sent ConfigChangedEvent: " + "BankConfig");
        return saved;
    }

	@Override
	public BranchDetailsRecord getBranchDetails(Long branchId) {
		Branch branch =	iConfigDao.getBranchDetails(branchId,"Active");
		return ObjectMapperUtils.map(branch, BranchDetailsRecord.class);

	}

	@Override
	public BranchDetailsRecord getBranchDetails(String branchCode) {

		Branch branch =	iConfigDao.getBranchDetails(branchCode,"Active");
		return ObjectMapperUtils.map(branch, BranchDetailsRecord.class);
	}
}
