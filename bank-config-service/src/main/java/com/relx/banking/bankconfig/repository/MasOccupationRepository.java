package com.relx.banking.bankconfig.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.relx.banking.bankconfig.entity.MasOccupation;

/**
 * @author Naveen.Sankhala
 * Oct 13, 2025
 */
@Repository
public interface MasOccupationRepository extends JpaRepository<MasOccupation, Long> {

}
