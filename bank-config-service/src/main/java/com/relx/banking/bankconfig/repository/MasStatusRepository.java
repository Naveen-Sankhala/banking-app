package com.relx.banking.bankconfig.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.relx.banking.bankconfig.entity.MasStatus;

/**
 * @author Naveen.Sankhala
 * Oct 9, 2025
 */
@Repository
public interface MasStatusRepository extends JpaRepository<MasStatus, Long> {

	Optional<MasStatus> findByStatusCodeContainingAndStatusTableContaining(String statusCode, String statusTable);

}
