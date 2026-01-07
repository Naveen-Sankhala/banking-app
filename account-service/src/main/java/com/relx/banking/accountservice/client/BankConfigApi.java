package com.relx.banking.accountservice.client;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.relx.banking.commondto.CityDto;
import com.relx.banking.commondto.GenderTitleDto;
import com.relx.banking.commondto.MasCurrencyDto;
import com.relx.banking.commondto.RelationDto;
import com.relx.banking.commondto.StateDto;
import com.relx.banking.commonrecord.BranchDetailsRecord;
import com.relx.banking.commonsecurity.FeignTokenInterceptor;

/**
 * @author Naveen.Sankhala
 * Oct 9, 2025
 */

@FeignClient(name = "bank-config-service", url = "${config-service.url}",configuration=FeignTokenInterceptor.class)
public interface BankConfigApi {

	@GetMapping("/common/all-config")
	Map<String, Object> getAllCommonConfiguration();

	@GetMapping("/common/by-key/{key}")
	Object getConfigByKey(@PathVariable String key);

	@GetMapping("/common/branch")
	BranchDetailsRecord getBranchDetails(@RequestParam("branch-id") Long branchId,
			@RequestParam("branch-code") String branchCode);

	@GetMapping("master/currency/{country-id}")
	MasCurrencyDto getCurrency(@PathVariable("country-id") Long countryId);

	@GetMapping("master/state/{country-id}")
	List<StateDto> getAllState(@PathVariable("country-id") Long countryId);

	@GetMapping("master/city/{state-id}")
	List<CityDto> getAllCity(@PathVariable("state-id") Long stateId);

	@GetMapping("master/state_city")
	String getCityNameAndStateNameDetails(@RequestParam("state-name") String stateName,@RequestParam("city-name") String cityName);

	@GetMapping("master/gender-title")
	List<GenderTitleDto> getGenderTitle();

	@GetMapping("master/relation")
	List<RelationDto> getMasRelation();

	@GetMapping("master/status/{status-code}/{status-table}")
	StateDto getMasStatus(@PathVariable("status-code") String statusCode,@PathVariable("status-table") String statusTable);

}
