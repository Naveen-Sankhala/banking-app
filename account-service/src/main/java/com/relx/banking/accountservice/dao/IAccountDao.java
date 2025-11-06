package com.relx.banking.accountservice.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.relx.banking.accountservice.entity.Account;
import com.relx.banking.accountservice.entity.AccountCategories;

/**
 * @author Naveen.Sankhala
 * Jun 3, 2025
 */
@Service
public interface IAccountDao {

	Account saveAccount(Account account);
	
	List<Account> getAccountInfoByCustomerId(Long customerId);
	
	List<Account> getAccountInfoByCustomerId(Long customerId, String status);

	Account getAccountInfoByAccountId(Long accountId, String status);

	Account getAccountInfoByAccountNumber(String accountNumber, String status);

	List<Account> getAccountInfoByAccountType(List<String> accountType, String status);
	
	Account getAccountInfoByCustomerIdAndAccType(Long customerId, String accountType);

	String generateAccountNumber(long branchId, long accCatId);

	AccountCategories getAccountCatgory(Long accCatId);

	AccountCategories getAccountCatgoryByShortName(String accCatSName);

	AccountCategories getAccountCatgory(String accCatName);

	Map<String, Object> getAllAccounts(int page, int size);

	

	

}
