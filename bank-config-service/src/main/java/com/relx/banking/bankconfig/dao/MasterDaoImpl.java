package com.relx.banking.bankconfig.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.relx.banking.bankconfig.entity.MasCast;
import com.relx.banking.bankconfig.entity.MasCity;
import com.relx.banking.bankconfig.entity.MasCurrency;
import com.relx.banking.bankconfig.entity.MasCustomerConstitution;
import com.relx.banking.bankconfig.entity.MasGenderTitle;
import com.relx.banking.bankconfig.entity.MasOccupation;
import com.relx.banking.bankconfig.entity.MasRelation;
import com.relx.banking.bankconfig.entity.MasReligion;
import com.relx.banking.bankconfig.entity.MasState;
import com.relx.banking.bankconfig.entity.MasStatus;
import com.relx.banking.bankconfig.repository.MasCastRepository;
import com.relx.banking.bankconfig.repository.MasCityRepository;
import com.relx.banking.bankconfig.repository.MasCountryRepository;
import com.relx.banking.bankconfig.repository.MasCurrencyRepository;
import com.relx.banking.bankconfig.repository.MasCustomerConstitutionRepository;
import com.relx.banking.bankconfig.repository.MasGenderTitleRepository;
import com.relx.banking.bankconfig.repository.MasOccupationRepository;
import com.relx.banking.bankconfig.repository.MasRelationRepository;
import com.relx.banking.bankconfig.repository.MasReligionRepository;
import com.relx.banking.bankconfig.repository.MasStateRepository;
import com.relx.banking.bankconfig.repository.MasStatusRepository;
import com.relx.banking.bankconfig.util.exceptionhandling.NotFoundException;

import lombok.RequiredArgsConstructor;

/**
 * @author Naveen.Sankhala
 * Oct 9, 2025
 */

@Service
@RequiredArgsConstructor
public class MasterDaoImpl implements IMasterDao {
	
	private final MasCityRepository masCityRepository;
	
	private final MasStateRepository masStateRepo;
	
	private final MasCountryRepository masCountryRepo;
	
	private final MasGenderTitleRepository masGenderTitleRepo;
	
	private final MasCurrencyRepository masCurrencyRepo;
	
	private final MasRelationRepository masRelationRepo;
	
	private final MasStatusRepository masStatusRepo;
	
	private final MasOccupationRepository masOcpRepo;
	private final MasReligionRepository masRelgnRepo;
	private final MasCastRepository masCastRepo;
	private final MasCustomerConstitutionRepository masCustConRepo;
	
	
	@Override
	public List<MasState> getAllState(Long countryId) {
		return masStateRepo.findByCountryId(countryId);
	}

	@Override
	public List<MasCity> getAllCity(Long stateId) {
		return masCityRepository.findByStateId(stateId);
	}
	
	public Map<String,Object> getStateAndCityInformation(String cityName, String stateName) {
		Map<String,Object> cityStateInfo = new HashMap<String, Object>();
		MasCity city = null;
		
		MasState state = masStateRepo.findByStateNameContaining(stateName).get();
		
		if(state != null)
			city = masCityRepository.findByCityNameContainingAndStateId(cityName,state.getStateId())
				.orElse(new MasCity());
		
		cityStateInfo.put("State", state);
		cityStateInfo.put("City", city);
		
		return cityStateInfo;
	}

	@Override
	public MasCurrency getCurrency(Long countryId) {
		return masCurrencyRepo.findByCountryId(countryId)
				.orElseThrow(() -> new NotFoundException("No Currency Details Found :::"));
	}

	@Override
	public List<MasGenderTitle> getGenderTitle() {
		return masGenderTitleRepo.findAll();
	}

	@Override
	public List<MasRelation> getMasRelation() {
		return masRelationRepo.findAll();
	}

	@Override
	public MasStatus getMasStatus(String statusCode, String statusTable) {
		
		return masStatusRepo.findByStatusCodeContainingAndStatusTableContaining(statusCode,statusTable)
				.orElseThrow(() -> new NotFoundException("No Status Details Found :::"));
	}

	@Override
	public List<MasOccupation> getMasOccupation() {
		return masOcpRepo.findAll();
	}

	@Override
	public List<MasReligion> getMasReligion() {
		return masRelgnRepo.findAll();
	}

	@Override
	public List<MasCast> getMasCast() {
		return masCastRepo.findAll();
	}

	@Override
	public List<MasCustomerConstitution> getMasCustomerConstitution() {
		return masCustConRepo.findAll();
	}

}
