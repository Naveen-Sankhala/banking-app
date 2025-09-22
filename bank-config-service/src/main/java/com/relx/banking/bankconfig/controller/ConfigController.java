package com.relx.banking.bankconfig.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.relx.banking.bankconfig.config.ConfigInitializer;
import com.relx.banking.bankconfig.service.IConfigService;

import lombok.RequiredArgsConstructor;

/**
 * @author Naveen.Sankhala
 * Sep 18, 2025
 */

@RequiredArgsConstructor
@RefreshScope
@RestController
@RequestMapping("/")
public class ConfigController {

	private static final Logger logger = LoggerFactory.getLogger(ConfigController.class);
	
	private final IConfigService iConfigService;
	
	private final  ConfigInitializer configInitializer;

	@GetMapping("bank-config")
	public ResponseEntity<?> getCurrentBalanceOfAccount()throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(iConfigService.getBankConfiguration());
	}
	
	@GetMapping("")
    public ResponseEntity<?> getAllCommonConfiguration() {
		return ResponseEntity.status(HttpStatus.OK).body(configInitializer.getAllCommonConfiguration());
    }

    @GetMapping("/{key}")
    public ResponseEntity<Object> getConfig(@PathVariable String key) {
        Object value = configInitializer.getConfig(key);
        if(value !=null)
			return ResponseEntity.status(HttpStatus.OK).body(value);
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
