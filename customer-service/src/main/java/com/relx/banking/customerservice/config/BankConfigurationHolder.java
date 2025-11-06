package com.relx.banking.customerservice.config;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.stereotype.Component;

import com.relx.banking.commondto.BankConfigurationDto;

/**
 * @author Naveen.Sankhala
 * Nov 4, 2025
 */

@Component
public class BankConfigurationHolder {

	private final AtomicReference<BankConfigurationDto> configRef = new AtomicReference<>();

	public void setConfig(BankConfigurationDto config) {
		if (configRef.get() == null && config != null) {
			configRef.set(config);
		}
	}

	public void updateConfig(BankConfigurationDto config) {
		if (config != null) {
			configRef.set(config);
		}
	}

	public BankConfigurationDto getConfig() {
		return configRef.get();
	}

	public boolean isInitialized() {
		return configRef.get() != null;
	}
	
	public LocalDate getBankDate() {
        BankConfigurationDto config = configRef.get();
        if (config == null || config.getBankDate() == null) {
            return null;
        }
        return config.getBankDate();
    }
	
	public LocalDateTime getBankDateTime() {
        BankConfigurationDto config = configRef.get();
        if (config == null || config.getBankDate() == null) {
            return null;
        }
        return config.getBankDate().atTime(LocalTime.now());
    }

}
