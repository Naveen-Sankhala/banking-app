package com.relx.banking.bankconfig.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.relx.banking.bankconfig.dao.IMasterDao;
import com.relx.banking.bankconfig.entity.MasCity;
import com.relx.banking.bankconfig.entity.MasCurrency;
import com.relx.banking.bankconfig.entity.MasGenderTitle;
import com.relx.banking.bankconfig.entity.MasRelation;
import com.relx.banking.bankconfig.entity.MasState;
import com.relx.banking.bankconfig.util.ObjectMapperUtils;
import com.relx.banking.commondto.CityDto;
import com.relx.banking.commondto.GenderTitleDto;
import com.relx.banking.commondto.MasCurrencyDto;
import com.relx.banking.commondto.RelationDto;
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
	public Object getMasOccupation() {
		return iMasterDao.getMasOccupation();
	}
	
	@Override
	public Object getMasReligion() {
		return iMasterDao.getMasReligion();
	}
	
	@Override
	public Object getMasCast() {
		return iMasterDao.getMasCast();
	}
	
	@Override
	public Object getMasCustomerConstitution() {
		return iMasterDao.getMasCustomerConstitution();
	}
	
	@Override
	public Object getMasStatus(String statusCode, String statusTable) {
		return iMasterDao.getMasStatus(statusCode,statusTable);
	}
}
