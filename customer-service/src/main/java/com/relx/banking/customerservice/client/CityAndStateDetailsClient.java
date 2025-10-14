package com.relx.banking.customerservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Naveen.Sankhala
 * Oct 7, 2025
 */
@FeignClient(name = "bank-config-service", url = "${bank-config-service.url}")
public interface CityAndStateDetailsClient {
	
	@GetMapping("config/")
	String getCityNameAndStateNameDetails(@RequestParam("state-name") String stateName,@RequestParam("city-name") String cityName);

}
