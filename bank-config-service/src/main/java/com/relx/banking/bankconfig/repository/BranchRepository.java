package com.relx.banking.bankconfig.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.relx.banking.bankconfig.entity.Branch;

/**
 * @author Naveen.Sankhala
 * Oct 9, 2025
 */

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
	
	Optional<Branch> findByBranchIdAndStatus(Long branchId,String status);
	
	Optional<Branch> findByBranchCodeAndStatus(String branchCode,String status);
	
	List<Branch> findByZrIdAndStatus(Long zrId,String status);

}
