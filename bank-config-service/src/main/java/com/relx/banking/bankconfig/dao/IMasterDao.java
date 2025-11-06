package com.relx.banking.bankconfig.dao;

import java.util.List;

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

/**
 * @author Naveen.Sankhala
 * Oct 9, 2025
 */

@Service
public interface IMasterDao {

	List<MasState> getAllState(Long countryId);

	List<MasCity> getAllCity(Long stateId);

	MasCurrency getCurrency(Long countryId);
	
	List<MasCurrency> getCurrency();

	List<MasGenderTitle> getGenderTitle();

	List<MasRelation>  getMasRelation();

	MasStatus getMasStatus(String statusCode, String statusTable);

	List<MasOccupation> getMasOccupation();

	List<MasReligion> getMasReligion();

	List<MasCast> getMasCast();

	List<MasCustomerConstitution> getMasCustomerConstitution();

	

}
