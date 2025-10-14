package com.relx.banking.bankconfig.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.relx.banking.bankconfig.entity.MasGenderTitle;

/**
 * @author Naveen.Sankhala
 * Oct 9, 2025
 */

@Repository
public interface MasGenderTitleRepository extends JpaRepository<MasGenderTitle, Long> {

}
