package com.relx.banking.bankconfig.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.relx.banking.bankconfig.dao.IMasterDao;
import com.relx.banking.bankconfig.entity.MasCast;
import com.relx.banking.bankconfig.entity.MasCity;
import com.relx.banking.bankconfig.entity.MasCurrency;
import com.relx.banking.bankconfig.entity.MasCustomerConstitution;
import com.relx.banking.bankconfig.entity.MasGenderTitle;
import com.relx.banking.bankconfig.entity.MasOccupation;
import com.relx.banking.bankconfig.entity.MasRelation;
import com.relx.banking.bankconfig.entity.MasReligion;
import com.relx.banking.bankconfig.entity.MasState;
import com.relx.banking.bankconfig.util.ObjectMapperUtils;
import com.relx.banking.commondto.CastDto;
import com.relx.banking.commondto.CityDto;
import com.relx.banking.commondto.CustomerConstitutionDto;
import com.relx.banking.commondto.GenderTitleDto;
import com.relx.banking.commondto.MasCurrencyDto;
import com.relx.banking.commondto.OccupationDto;
import com.relx.banking.commondto.RelationDto;
import com.relx.banking.commondto.ReligionDto;
import com.relx.banking.commondto.StateDto;

import lombok.RequiredArgsConstructor;

/**
 * @author Naveen.Sankhala
 * Oct 9, 2025
 */
@Service
@RequiredArgsConstructor
public class MasterServiceImpl implements IMasterService {
	private static final Logger logger = LoggerFactory.getLogger(MasterServiceImpl.class);
	
	private final IMasterDao iMasterDao;

	@Override
	public List<StateDto> getAllState(Long countryId) {
		List<MasState> stateList = iMasterDao.getAllState(countryId);
		return ObjectMapperUtils.mapAll(stateList, StateDto.class);
		
	}

	@Override
	public List<CityDto> getAllCity(Long stateId) {
		List<MasCity> cityList = iMasterDao.getAllCity(stateId);
		return ObjectMapperUtils.mapAll(cityList, CityDto.class);
	}

	@Override
	public MasCurrencyDto getCurrency(Long countryId) {
		MasCurrency currency= iMasterDao.getCurrency(countryId);
		return ObjectMapperUtils.map(currency, MasCurrencyDto.class);
	}
	
	@Override
	public List<MasCurrencyDto> getMasCurrency() {
		List<MasCurrency> currencyList= iMasterDao.getCurrency();
		return ObjectMapperUtils.mapAll(currencyList, MasCurrencyDto.class);
	}

	@Override
	public List<GenderTitleDto> getGenderTitle() {
		List<MasGenderTitle> genderTitlelist = iMasterDao.getGenderTitle();
		return ObjectMapperUtils.mapAll(genderTitlelist, GenderTitleDto.class);
	}

	@Override
	public List<RelationDto> getMasRelation() {
		List<MasRelation>  relationList = iMasterDao.getMasRelation();
		return ObjectMapperUtils.mapAll(relationList, RelationDto.class);
	}

	@Override
	public List<OccupationDto> getMasOccupation() {
		List<MasOccupation> masOccupation = iMasterDao.getMasOccupation();
		return ObjectMapperUtils.mapAll(masOccupation, OccupationDto.class);
	}
	
	@Override
	public List<ReligionDto> getMasReligion() {
		List<MasReligion> masReligion = iMasterDao.getMasReligion();
		return ObjectMapperUtils.mapAll(masReligion, ReligionDto.class);
	}
	
	@Override
	public List<CastDto> getMasCast() {
		List<MasCast> masCast =iMasterDao.getMasCast();
		return ObjectMapperUtils.mapAll(masCast, CastDto.class);
	}
	
	@Override
	public List<CustomerConstitutionDto> getMasCustomerConstitution() {
		List<MasCustomerConstitution> masCustCont =  iMasterDao.getMasCustomerConstitution();
		return ObjectMapperUtils.mapAll(masCustCont, CustomerConstitutionDto.class);
	}
	
	@Override
	public Object getMasStatus(String statusCode, String statusTable) {
		return iMasterDao.getMasStatus(statusCode,statusTable);
	}
}
