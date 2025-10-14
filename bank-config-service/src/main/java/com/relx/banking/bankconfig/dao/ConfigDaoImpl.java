package com.relx.banking.bankconfig.dao;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.relx.banking.bankconfig.entity.BankConfiguration;
import com.relx.banking.bankconfig.entity.Branch;
import com.relx.banking.bankconfig.repository.BankConfigurationRepository;
import com.relx.banking.bankconfig.repository.BranchRepository;

import lombok.RequiredArgsConstructor;

/**
 * @author Naveen.Sankhala
 * Sep 18, 2025
 */
@Service
@RequiredArgsConstructor
public class ConfigDaoImpl implements IConfigDao {
	
	private static final Logger logger = LoggerFactory.getLogger(ConfigDaoImpl.class);
	
	
	private final BankConfigurationRepository bnkConfigRepo;
	
	private final BranchRepository branchRepo;
	
	@Override
	public BankConfiguration saveBankConfiguration(BankConfiguration config) {
		return bnkConfigRepo.save(config);
	}
	
	@Override
	 public BankConfiguration getBankConfiguration() {
	        return bnkConfigRepo.findTopByOrderByConfigIdAsc()
	                .orElseThrow(() -> new IllegalStateException("Bank Configuration Missing"));
	 }

	@Override
	public Object getBranchDetails(Long branchId, String status) {
		return branchRepo.findByBranchIdAndStatus(branchId, status)
				 .orElseThrow(() -> new IllegalStateException("No Branch Details Found"));
	}

	@Override
	public Object getBranchDetails(String branchCode, String status) {
		return branchRepo.findByBranchCodeAndStatus(branchCode, status)
				 .orElseThrow(() -> new IllegalStateException("No Branch Details Found"));
	}
	
	@Override
	public List<Branch> getBranchDetailsByZoneId(Long zrId, String status) {
		return branchRepo.findByZrIdAndStatus(zrId, status);

	}

	
}
