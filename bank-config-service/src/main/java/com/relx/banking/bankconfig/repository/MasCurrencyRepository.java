package com.relx.banking.bankconfig.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.relx.banking.bankconfig.entity.MasCurrency;

/**
 * @author Naveen.Sankhala
 * Oct 9, 2025
 */

@Repository
public interface MasCurrencyRepository extends JpaRepository<MasCurrency, Long>{

	Optional<MasCurrency> findByCountryId(Long countryId);
	
	List<MasCurrency> findAll();

}
