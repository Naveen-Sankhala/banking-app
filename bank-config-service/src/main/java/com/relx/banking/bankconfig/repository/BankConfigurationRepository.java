package com.relx.banking.bankconfig.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.relx.banking.bankconfig.entity.BankConfiguration;

/**
 * @author Naveen.Sankhala
 * Sep 18, 2025
 */
@Repository
public interface BankConfigurationRepository extends JpaRepository<BankConfiguration, Long>{
	
	Optional<BankConfiguration> findTopByOrderByConfigIdAsc();

}
