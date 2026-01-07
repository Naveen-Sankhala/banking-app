package com.relx.banking.bankconfig.service;

import org.springframework.stereotype.Service;

import com.relx.banking.bankconfig.entity.BankConfiguration;
import com.relx.banking.commondto.BankConfigurationDto;
import com.relx.banking.commonrecord.BranchDetailsRecord;

/**
 * @author Naveen.Sankhala
 * Sep 18, 2025
 */
@Service
public interface IConfigService {

	BankConfigurationDto getBankConfiguration();

	BranchDetailsRecord getBranchDetails(Long branchId);

	BranchDetailsRecord getBranchDetails(String branchCode);

}
