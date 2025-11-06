package com.relx.banking.bankconfig.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.relx.banking.bankconfig.entity.BankConfiguration;
import com.relx.banking.bankconfig.entity.Branch;

/**
 * @author Naveen.Sankhala
 * Sep 18, 2025
 */
@Service
public interface IConfigDao {

	BankConfiguration saveBankConfiguration(BankConfiguration config);
	
	BankConfiguration getBankConfiguration();

	Branch getBranchDetails(Long branchId, String status);

	Branch getBranchDetails(String branchCode, String status);

	List<Branch> getBranchDetailsByZoneId(Long zrId, String status);

}
