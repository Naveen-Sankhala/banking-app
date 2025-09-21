package com.relx.banking.accountservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.relx.banking.accountservice.entity.AccountCategories;

/**
 * @author Naveen.Sankhala
 * Sep 15, 2025
 */
@Repository
public interface AccountCategoriesRepository extends JpaRepository<AccountCategories, Long> {

	Optional<AccountCategories> findByaccCatIdAndStatus(Long accCatId,String status);
	
	Optional<AccountCategories> findByAccCatSNameAndStatus(String accCatSName,String status);
	
	Optional<AccountCategories> findByAccCatNameAndStatus(String accCatName,String status);

}
