package com.relx.banking.bankconfig.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.relx.banking.bankconfig.entity.MasState;

/**
 * @author Naveen.Sankhala
 * Oct 9, 2025
 */

@Repository
public interface MasStateRepository extends JpaRepository<MasState, Long> {

	List<MasState> findByCountryId(Long countryId);

	Optional<MasState> findByStateNameContaining(String stateName);

}
