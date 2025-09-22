package com.relx.banking.customerservice.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.relx.banking.customerservice.dto.JointAccountDto;

/**
 * @author Naveen.Sankhala
 * Sep 11, 2025
 */
@FeignClient(name = "account-service", url = "${account-service.url}")
public interface AccountClient {

	@GetMapping("/accounts/customer/{customerId}")
    List<JointAccountDto> getAccountsByCustomerId(@PathVariable("customerId") Long customerId);
}
