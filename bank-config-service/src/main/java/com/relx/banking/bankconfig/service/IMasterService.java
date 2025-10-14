package com.relx.banking.bankconfig.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.relx.banking.commondto.CityDto;
import com.relx.banking.commondto.GenderTitleDto;
import com.relx.banking.commondto.MasCurrencyDto;
import com.relx.banking.commondto.RelationDto;
import com.relx.banking.commondto.StateDto;

/**
 * @author Naveen.Sankhala
 * Oct 9, 2025
 */

@Service
public interface IMasterService {

	List<StateDto> getAllState(Long countryId);

	List<CityDto> getAllCity(Long stateId);

	MasCurrencyDto getCurrency(Long countryId);

	List<GenderTitleDto> getGenderTitle();

	List<RelationDto> getMasRelation();

	Object getMasStatus(String statusCode, String statusTable);

	Object getMasOccupation();

	Object getMasReligion();

	Object getMasCast();

	Object getMasCustomerConstitution();

}
