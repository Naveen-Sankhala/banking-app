package com.relx.banking.accountservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.relx.banking.accountservice.dto.AccountRequestDto;
import com.relx.banking.accountservice.dto.AccountResponseDto;
import com.relx.banking.accountservice.service.IAccountService;
import com.relx.banking.accountservice.util.exceptionhandling.ApiResponse;

/**
 * @author Naveen Sankhala
 */
@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins = "${ui.cross.url}")
//@Tag(name ="Account-controller", description = "Set of end points retrieving & store Account details")
public class AccountController {

	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	//add initBinder ... to convert trim input string
	//remove leading and trailing whitespace
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		webDataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	@Autowired
	private IAccountService iAccountService;
	
	@Autowired
	private MessageSource messageSource;
	
	@PostMapping("")
	public ResponseEntity<?> createNewAccount(@RequestBody AccountRequestDto accountRequestDto) throws Exception{
		AccountResponseDto result = iAccountService.createNewAccount(accountRequestDto);
		if(result !=null)
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(true,String.format("Account create successfuly")));
		else
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(false,messageSource.getMessage("4", null,LocaleContextHolder.getLocale())));
	}

	@GetMapping("/{accountId}")
	public ResponseEntity<?> getAccountDetails(@PathVariable Long accountId)throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(iAccountService.getAccountDetails(accountId));
	}
	
	@GetMapping("/by-customer/{customerId}")
	public ResponseEntity<?> getAllAccountForCutomer(@PathVariable String customerId)throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(iAccountService.getAllAccountForCutomer(customerId));
	}
	
	@PatchMapping("/active/{accountId}")
	public ResponseEntity<?> activeDeactiveAccount(@PathVariable Long accountId)throws Exception{
		Boolean activated = iAccountService.activeDeactiveAccount(accountId);
		if(activated)
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(true,String.format("Account Activated successfuly :: ")));
		else
			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(false,messageSource.getMessage("4", null,LocaleContextHolder.getLocale())));

	}
	
	@GetMapping("/{accountId}/balance")
	public ResponseEntity<?> getCurrentBalanceOfAccount(@PathVariable Long accountId)throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(iAccountService.getCurrentBalanceOfAccount(accountId));
	}
	
	@PutMapping("{accountId}")
	public ResponseEntity<?> updateAccountMetaData(@PathVariable Long accountId)throws Exception{
		return ResponseEntity.status(HttpStatus.OK).body(iAccountService.updateAccountMetaData(accountId));
	}
	
	@GetMapping
    public ResponseEntity<?> getAccounts(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

		return ResponseEntity.ok( iAccountService.getAllAccounts(page, size));
	}
}
