package com.relx.banking.bankconfig.dao;

import org.springframework.stereotype.Service;

import com.relx.banking.bankconfig.entity.BankConfiguration;

/**
 * @author Naveen.Sankhala
 * Sep 18, 2025
 */
@Service
public interface IConfigDao {

	BankConfiguration saveBankConfiguration(BankConfiguration config);
	
	BankConfiguration getBankConfiguration();

}
