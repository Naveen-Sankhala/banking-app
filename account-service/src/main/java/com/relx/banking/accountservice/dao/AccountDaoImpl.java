package com.relx.banking.accountservice.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.relx.banking.accountservice.dto.AccountSummary;
import com.relx.banking.accountservice.dto.AccountSummaryDto;
import com.relx.banking.accountservice.entity.Account;
import com.relx.banking.accountservice.entity.AccountCategories;
import com.relx.banking.accountservice.repository.AccountCategoriesRepository;
import com.relx.banking.accountservice.repository.AccountRepository;
import com.relx.banking.accountservice.util.AccountConstant;
import com.relx.banking.accountservice.util.exceptionhandling.NotFoundException;

/**
 * @author Naveen.Sankhala
 * Jun 3, 2025
 */
@Service
public class AccountDaoImpl implements IAccountDao {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AccountCategoriesRepository accCatRepository;

	@Override
	public Account saveAccount(Account account) {
		return accountRepository.save(account);
	} 
	
	@Override
	public List<Account> getAccountInfoByCustomerId(Long customerId) {
		return accountRepository.findByCustomerId(customerId);
	}
	
	@Override
	public List<Account> getAccountInfoByCustomerId(Long customerId,String status) {
		return accountRepository.findByCustomerIdAndStatus(customerId,status);
	}
	
	@Override
	public Account getAccountInfoByAccountId(Long accountId,String status) {
		return accountRepository.findByAccountIdAndStatus(accountId,status)
				.orElseThrow(()-> new NotFoundException("No Account Found For this Account ID :::"+accountId));
	}
	
	@Override
	public Account getAccountInfoByAccountNumber(String accountNumber,String status) {
		return accountRepository.findByAccountNumberAndStatus(accountNumber,status)
				.orElseThrow(()-> new NotFoundException("No Account Found For this Account Number :::"+accountNumber));
	}
	
	@Override
	public List<Account> getAccountInfoByAccountType(List<String> accountType,String status) {
		return accountRepository.findByAccountTypeInAndStatus(accountType,status);
	}
	
	@Override
	public Account getAccountInfoByCustomerIdAndAccType(Long customerId, String accountType) {
		return accountRepository.findByCustomerIdAndAccountType(customerId,accountType);
	}
	
	@Override
	public String generateAccountNumber(long branchId,long accCatId) {
		return  accountRepository.generateAccountNumber(branchId, accCatId, "StandardRule1")
				.orElseThrow(()-> new NotFoundException("No Account Number Generate :::"));
	}

	@Override
	public AccountCategories getAccountCatgory(Long accCatId) {
		return accCatRepository.findByaccCatIdAndStatus(accCatId,"ACTIVE")
				.orElseThrow(()-> new NotFoundException("No Account Category Found :::"));
	}

	@Override
	public AccountCategories getAccountCatgoryByShortName(String accCatSName) {
		return accCatRepository.findByAccCatSNameAndStatus(accCatSName,"ACTIVE")
				.orElseThrow(()-> new NotFoundException("No AccountCategory Found :::"));
	}

	@Override
	public AccountCategories getAccountCatgory(String accCatName) {
		return accCatRepository.findByAccCatNameAndStatus(accCatName,"ACTIVE")
				.orElseThrow(()-> new NotFoundException("No Account Category Found :::"));
	}

	@Override
	public Map<String, Object> getAllAccounts(int page, int size) {
		Map<String, Object> response = new HashMap<>();

		Pageable paging = PageRequest.of(page, size, Sort.by("accountNumber"));

		Page<AccountSummary> accountSummaryDto = accountRepository.findAllSummaries(paging);

		response.put("accountsummary", accountSummaryDto.getContent());
		response.put(AccountConstant.CURRENTPAGE, accountSummaryDto.getNumber());
		response.put(AccountConstant.TOTALITEMS, accountSummaryDto.getTotalElements());
		response.put(AccountConstant.TOTALPAGES, accountSummaryDto.getTotalPages());
		response.put(AccountConstant.HASNEXT, accountSummaryDto.hasNext());
		response.put(AccountConstant.HASPREVIOUS,accountSummaryDto.hasPrevious());
		return response;
	}

}
