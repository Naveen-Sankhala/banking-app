package com.relx.banking.customerservice.client;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.relx.banking.commondto.BankConfigurationDto;
import com.relx.banking.commondto.CastDto;
import com.relx.banking.commondto.CustomerConstitutionDto;
import com.relx.banking.commondto.GenderTitleDto;
import com.relx.banking.commondto.MasCurrencyDto;
import com.relx.banking.commondto.OccupationDto;
import com.relx.banking.commondto.RelationDto;
import com.relx.banking.commondto.ReligionDto;
import com.relx.banking.customerservice.config.ConfigListener;
import com.relx.banking.customerservice.util.CustomerUtil;

import lombok.RequiredArgsConstructor;

/**
 * @author Naveen.Sankhala
 * Sep 19, 2025
 */
@Service
@RequiredArgsConstructor
public class BankConfigClient {
	private static final Logger logger = LoggerFactory.getLogger(BankConfigClient.class);

	private final ConfigListener listener;

	/*
	 * public LocalDate getBankDate() { return (LocalDate)
	 * listener.getConfig("bankDate"); }
	 */

	public BankConfigurationDto getBankConfig() {

		Object bankConfigObj = listener.getConfig("BankConfig");
		logger.info("Get BankConfig :::"+ bankConfigObj);
		return (BankConfigurationDto) bankConfigObj;
	}

	public MasCurrencyDto getMasCurrency(String curCode ,Long countryId) {

		Object obj = listener.getConfig("MasCurrency");
		List<MasCurrencyDto> list = CustomerUtil.getMapObjectValue("MasCurrency", obj, MasCurrencyDto.class);

		return list.stream()
				.filter(c -> (countryId != null && countryId.equals(c.getCountryId())) ||
						(countryId == null && curCode != null && curCode.equalsIgnoreCase(c.getCurrencyCode())))
				.findFirst()
				.orElse(null);
	}

	public GenderTitleDto getMasGenderTitle(String genderCode) {

		Object obj = listener.getConfig("MasGenderTitle");
		List<GenderTitleDto> list = CustomerUtil.getMapObjectValue("MasGenderTitle", obj, GenderTitleDto.class);

		return list.stream()
				.filter(c -> c.getGenderCode() != null && c.getGenderCode().equals(genderCode.toUpperCase()))
				.findFirst()
				.orElse(null);
	}
	public RelationDto getMasRelation(String relationCode) {
		Object obj = listener.getConfig("MasRelation");
		List<RelationDto> list = CustomerUtil.getMapObjectValue("MasRelation", obj, RelationDto.class);

		return list.stream()
				.filter(c -> c.getRelationCode() != null && c.getRelationCode().equals(relationCode.toUpperCase()))
				.findFirst()
				.orElse(null);
	}

	public OccupationDto getMasOccupation(String ocpName) {
		Object obj = listener.getConfig("MasRelation");
		List<OccupationDto> list = CustomerUtil.getMapObjectValue("MasOccupation", obj, OccupationDto.class);

		return list.stream()
				.filter(c -> c.getEnglishName() != null && c.getEnglishName().equalsIgnoreCase(ocpName))
				.findFirst()
				.orElse(null);
	}

	public ReligionDto getMasReligion(String rlgnCode) {
		Object obj = listener.getConfig("MasReligion");
		List<ReligionDto> list = CustomerUtil.getMapObjectValue("MasReligion", obj, ReligionDto.class);

		return list.stream()
				.filter(c -> c.getRlgnCode() != null && c.getRlgnCode().equals(rlgnCode))
				.findFirst()
				.orElse(null);
	}

	public CastDto getMasCast(String castCode) {
		Object obj = listener.getConfig("MasCast");
		List<CastDto> list = CustomerUtil.getMapObjectValue("MasCast", obj, CastDto.class);

		return list.stream()
				.filter(c -> c.getCastCode() != null && c.getCastCode().equals(castCode))
				.findFirst()
				.orElse(null);
	}

	public CustomerConstitutionDto getMasCustomerConst(String conCode) {
		Object obj = listener.getConfig("MasCustomerConst");
		List<CustomerConstitutionDto> list = CustomerUtil.getMapObjectValue("MasCustomerConst", obj, CustomerConstitutionDto.class);

		return list.stream()
				.filter(c -> c.getConCode() != null && c.getConCode().equals(conCode))
				.findFirst()
				.orElse(null);
	}
}
