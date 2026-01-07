package com.relx.banking.notificationservice.client;

import java.util.HashMap;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Naveen.Sankhala
 * Dec 10, 2025
 */
@FeignClient(name = "user-management-service", url = "${user-service.url}")
public interface UserManagmentApi {

	@GetMapping("authority")
	HashMap<String, Object> getAuthority(@RequestParam("user-id") Long userId,@RequestParam("branch-id") Long branchId);
	
	@GetMapping("users")
	public ResponseEntity<?> findUser(@RequestParam("user-name")String userName,@RequestParam("branchId")Long branchId);

}
