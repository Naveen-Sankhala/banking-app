package com.relx.banking.accountservice.service;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.relx.banking.accountservice.dto.AccountRequestDto;
import com.relx.banking.accountservice.dto.AccountResponseDto;

/**
 * @author Naveen Sankhala
 */
@Service
public interface IAccountService {

	AccountResponseDto createNewAccount(AccountRequestDto accountRequestDto);

	AccountResponseDto getAccountDetails(Long accountId);

	Object getAllAccountForCutomer(String customerId);

	Object updateAccountMetaData(Long accountId);

	Boolean activeDeactiveAccount(Long accountId);

	BigDecimal getCurrentBalanceOfAccount(Long accountId);

	Map<String, Object> getAllAccounts(int page, int size);

}
