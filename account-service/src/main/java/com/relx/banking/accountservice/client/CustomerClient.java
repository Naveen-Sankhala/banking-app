package com.relx.banking.accountservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.relx.banking.accountservice.dto.CustomerDto;

/**
 * @author Naveen.Sankhala
 * Sep 11, 2025
 */

@FeignClient(name = "customer-service", url = "${customer.service.url}")
public interface CustomerClient {

	@GetMapping("/customer")
    CustomerDto getCustomerDetails(@RequestParam("customerId") Long customerId);

	@GetMapping("/customer")
	CustomerDto getCustomerDetails(@RequestParam("cif-no") String cifNo);
}
