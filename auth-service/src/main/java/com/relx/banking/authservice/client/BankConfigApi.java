package com.relx.banking.authservice.client;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.relx.banking.commonrecord.BranchDetailsRecord;

/**
 * @author Naveen.Sankhala
 * Oct 9, 2025
 */

@FeignClient(name = "bank-config-service", url = "${config-service.url}")
public interface BankConfigApi {
	
	@GetMapping("/common/all-config")
	Map<String, Object> getAllCommonConfiguration();
	
	@GetMapping("/common/by-key/{key}")
	Object getConfigByKey(@PathVariable String key);

	@GetMapping("/common/branch")
	BranchDetailsRecord getBranchDetails(@RequestParam("branch-id") Long branchId,@RequestParam("branch-code") String branchCode);
	
}
