package com.relx.banking.bankconfig.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.relx.banking.bankconfig.service.IMasterService;

import jakarta.ws.rs.PathParam;
import lombok.RequiredArgsConstructor;

/**
 * @author Naveen.Sankhala
 * Oct 9, 2025
 */

@RequiredArgsConstructor
@RefreshScope
@RestController
@RequestMapping("/")
public class MasterController {
	
	private static final Logger logger = LoggerFactory.getLogger(MasterController.class);
	
	private final IMasterService iMasterService;
	
	
	@GetMapping("state/{country-id}")
    public ResponseEntity<?> getAllState(@PathVariable("country-id") Long countryId) {
		return ResponseEntity.status(HttpStatus.OK).body(iMasterService.getAllState(countryId));
    }
	

	@GetMapping("city/{state-id}")
    public ResponseEntity<?> getAllCity(@PathVariable("state-id") Long stateId) {
		return ResponseEntity.status(HttpStatus.OK).body(iMasterService.getAllCity(stateId));
    }
	
	@GetMapping("currency/{country-id}")
    public ResponseEntity<?> getCurrency(@PathVariable("country-id") Long countryId) {
		return ResponseEntity.status(HttpStatus.OK).body(iMasterService.getCurrency(countryId));
    }
	
	@GetMapping("gender-title")
    public ResponseEntity<?> getGenderTitle() {
		return ResponseEntity.status(HttpStatus.OK).body(iMasterService.getGenderTitle());
    }
	
	
	@GetMapping("relation")
    public ResponseEntity<?> getMasRelation() {
		return ResponseEntity.status(HttpStatus.OK).body(iMasterService.getMasRelation());
    }
	
	
	@GetMapping("status/{status-code}/{status-table}")
    public ResponseEntity<?> getMasStatus(@PathVariable("status-code") String statusCode,@PathVariable("status-table") String statusTable) {
		return ResponseEntity.status(HttpStatus.OK).body(iMasterService.getMasStatus(statusCode,statusTable));
    }
	
//	@GetMapping("country-id")
//    public ResponseEntity<?> getCurrency(@PathVariable String countryId) {
//		return ResponseEntity.status(HttpStatus.OK).body(iMasterService.getCurrency(countryId));
//    }

}
