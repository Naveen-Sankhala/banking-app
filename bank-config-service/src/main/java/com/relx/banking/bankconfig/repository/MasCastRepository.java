package com.relx.banking.bankconfig.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.relx.banking.bankconfig.entity.MasCast;

/**
 * @author Naveen.Sankhala
 * Oct 13, 2025
 */
@Repository
public interface MasCastRepository extends JpaRepository<MasCast, Long> {

}
