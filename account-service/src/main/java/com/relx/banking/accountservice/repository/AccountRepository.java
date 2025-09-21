package com.relx.banking.accountservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.relx.banking.accountservice.dto.AccountSummary;
import com.relx.banking.accountservice.dto.AccountSummaryDto;
import com.relx.banking.accountservice.entity.Account;

/**
 * @author Naveen.Sankhala
 * Jun 3, 2025
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{

	@Query(value = "SELECT generate_account_number(:branchId, :accCatId, :formulaName)", nativeQuery = true)
	Optional<String> generateAccountNumber(@Param("branchId") long branchId,
			@Param("accCatId") long accCatId,
			@Param("formulaName") String formulaName);

	Optional<Account> findByAccountNumberAndStatus(String accountNumber,String status);

	List<Account> findByCustomerIdAndStatus(Long customerId,String status);

	List<Account> findByCustomerId(Long customerId);

	List<Account> findByAccountTypeInAndStatus(List<String>accountType,String status);

	Account findByCustomerIdAndAccountType(Long customerId, String accountType);

	@Query("SELECT DISTINCT a FROM Account a " +
			"LEFT JOIN FETCH a.jointHolders " +
			"LEFT JOIN FETCH a.accountInfo")
	List<Account> findAllAccountsWithDetails();

	// ✅ Fetch full account details for a single record (avoids N+1)
	@Query("SELECT a FROM Account a " +
			"LEFT JOIN FETCH a.jointHolders " +
			"LEFT JOIN FETCH a.accountInfo " +
			"WHERE a.accountId = :accountId AND a.status = :status" )
	Optional<Account> findByAccountIdAndStatus(@Param("accountId") Long accountId,@Param("status") String status);


	@EntityGraph(attributePaths = {"jointHolders", "accountInfo"})
	@Query("SELECT a FROM Account a WHERE a.accountId = :id")
	Optional<Account> findByIdWithGraph(@Param("id") Long id);

	/*
	@Query("SELECT new com.relx.banking.accountservice.dto.AccountSummaryDto(a.accountId,a.balance, a.branchCode, i.title, h.name) " +
			"FROM Account a " +
			"LEFT JOIN a.accountInfo i " +
			"LEFT JOIN a.jointHolders h " +
			"WHERE a.accountId = :accountId")
	Optional<AccountSummaryDto> getAccountSummary(@Param("accountId") Long accountId);
	*/

	// ✅ Fetch paginated lightweight account summaries (good for lists)
//    @Query("SELECT new com.relx.banking.accountservice.dto.AccountSummaryDto(a.accountId, a.balance, a.branchId, i.nameTitle, i.accInfoName) " +
//           "FROM Account a " +
//           "JOIN a.accountInfo i " +
//           "JOIN a.jointHolders h")
//    Page<AccountSummaryDto> findAllSummaries(Pageable pageable);
    
    @Query("SELECT a FROM Account a JOIN a.accountInfo i JOIN a.jointHolders h")
    Page<AccountSummary> findAllSummaries(Pageable pageable);

	
}
