package com.relx.banking.customerservice.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.relx.banking.commondto.BranchDetailsDto;
import com.relx.banking.commondto.CityDto;
import com.relx.banking.commondto.GenderTitleDto;
import com.relx.banking.commondto.MasCurrencyDto;
import com.relx.banking.commondto.RelationDto;
import com.relx.banking.commondto.StateDto;
import com.relx.banking.customerservice.dto.JointAccountDto;

/**
 * @author Naveen.Sankhala
 * Oct 9, 2025
 */

@FeignClient(name = "bank-config-service", url = "${config-service.url}")
public interface BankConfigApi {

	@GetMapping("/branch")
    BranchDetailsDto getBranchDetails(@RequestParam("branch-id") Long branchId,
    		@RequestParam("branch-code") String branchCode);
	
	@GetMapping("currency/{country-id}")
    MasCurrencyDto getCurrency(@PathVariable("country-id") Long countryId);
	
	@GetMapping("state/{country-id}")
    List<StateDto> getAllState(@PathVariable("country-id") Long countryId);

	@GetMapping("city/{state-id}")
	List<CityDto> getAllCity(@PathVariable("state-id") Long stateId);
	
	@GetMapping("gender-title")
	List<GenderTitleDto> getGenderTitle();
	
	@GetMapping("relation")
	List<RelationDto> getMasRelation();
	
	@GetMapping("status/{status-code}/{status-table}")
    StateDto getMasStatus(@PathVariable("status-code") String statusCode,@PathVariable("status-table") String statusTable);
	
}
