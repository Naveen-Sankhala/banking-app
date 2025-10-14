package com.relx.banking.bankconfig.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.relx.banking.bankconfig.entity.BankZoneRegion;

/**
 * @author Naveen.Sankhala
 * Oct 9, 2025
 */

@Repository
public interface BankZoneRegionRepository extends JpaRepository<BankZoneRegion, Long> {
	
	List<BankZoneRegion> findAll();

}
