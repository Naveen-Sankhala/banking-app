package com.relx.banking.transactionservice.client;

import java.util.HashMap;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.relx.banking.commonsecurity.FeignTokenInterceptor;

/**
 * @author Naveen.Sankhala
 * Dec 10, 2025
 */
@FeignClient(name = "user-management-service", url = "${user-service.url}",configuration=FeignTokenInterceptor.class)
public interface UserManagmentApi {

	@GetMapping("authority")
	HashMap<String, Object> getAuthority(@RequestParam("user-id") Long userId,@RequestParam("branch-id") Long branchId);

}
