package com.relx.banking.customerservice.client;

import org.springframework.stereotype.Service;

import com.relx.banking.commondto.BankConfigurationDto;
import com.relx.banking.customerservice.config.ConfigListener;

import lombok.RequiredArgsConstructor;

/**
 * @author Naveen.Sankhala
 * Sep 19, 2025
 */
@Service
@RequiredArgsConstructor
public class BankConfigClient {
	
	private final ConfigListener listener;

	/*
	 * public LocalDate getBankDate() { return (LocalDate)
	 * listener.getConfig("bankDate"); }
	 */

    public BankConfigurationDto getBankConfig() {
        return (BankConfigurationDto) listener.getConfig("bankConfig");
    }
}
