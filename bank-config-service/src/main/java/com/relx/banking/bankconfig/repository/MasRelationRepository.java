package com.relx.banking.bankconfig.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.relx.banking.bankconfig.entity.MasRelation;

/**
 * @author Naveen.Sankhala
 * Oct 9, 2025
 */

@Repository
public interface MasRelationRepository extends JpaRepository<MasRelation, Long> {

}
