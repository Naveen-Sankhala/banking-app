package com.relx.banking.bankconfig.dao;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.relx.banking.bankconfig.entity.BankConfiguration;
import com.relx.banking.bankconfig.repository.BankConfigurationRepository;

import lombok.RequiredArgsConstructor;

/**
 * @author Naveen.Sankhala
 * Sep 18, 2025
 */
@Service
@RequiredArgsConstructor
public class ConfigDaoImpl implements IConfigDao {
	
	private static final Logger logger = LoggerFactory.getLogger(ConfigDaoImpl.class);
	
	
	private final BankConfigurationRepository bnkConfigRepo;
	
	@Override
	public BankConfiguration saveBankConfiguration(BankConfiguration config) {
		return bnkConfigRepo.save(config);
	}
	
	@Override
	 public BankConfiguration getBankConfiguration() {
	        return bnkConfigRepo.findTopByOrderByConfigIdAsc()
	                .orElseThrow(() -> new IllegalStateException("Bank Configuration Missing"));
	 }

	
}
