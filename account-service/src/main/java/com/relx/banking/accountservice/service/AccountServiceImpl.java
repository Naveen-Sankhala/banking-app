package com.relx.banking.accountservice.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.relx.banking.accountservice.client.CustomerClient;
import com.relx.banking.accountservice.dao.IAccountDao;
import com.relx.banking.accountservice.dto.AccountRequestDto;
import com.relx.banking.accountservice.dto.AccountResponseDto;
import com.relx.banking.accountservice.dto.CustomerDto;
import com.relx.banking.accountservice.entity.Account;
import com.relx.banking.accountservice.entity.AccountCategories;
import com.relx.banking.accountservice.entity.AccountInformation;
import com.relx.banking.accountservice.entity.Branch;
import com.relx.banking.accountservice.entity.JointAccountHolder;
import com.relx.banking.accountservice.mappers.AccountInformationMapper;
import com.relx.banking.accountservice.mappers.AccountMapper;
import com.relx.banking.accountservice.util.ObjectMapperUtils;
import com.relx.banking.accountservice.util.exceptionhandling.AccountAlreadyExistsException;
import com.relx.banking.accountservice.util.exceptionhandling.InvalidCustomerException;
import com.relx.banking.accountservice.util.exceptionhandling.NotFoundException;

/**
 * @author Naveen Sankhala
 */
@Service
public class AccountServiceImpl implements IAccountService {
	private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
	
	@Autowired
	private IAccountDao iAccountDao;
	
	@Autowired 
	private CustomerClient customerClient;
	
	@Override
	public AccountResponseDto createNewAccount(AccountRequestDto accReqDto) {
		List<JointAccountHolder> jointAccHolderList = new ArrayList<JointAccountHolder>();
		CustomerDto customer = customerClient.getCustomerDetails(accReqDto.getCifNo());
	    if (customer ==null) {
	        throw new InvalidCustomerException("Customer does not exist");
	    }else if(customer.status().equals("InActive")) {
	    	throw new InvalidCustomerException("Customer status is Inactive");
	    }
	    
	    Account _accNo = iAccountDao.getAccountInfoByCustomerIdAndAccType(customer.customerId(),accReqDto.getAccountType());
	    
	    if(_accNo !=null && _accNo.getAccountType().equals(accReqDto.getAccountType()))
	    	throw new AccountAlreadyExistsException("Customer already has an account of type: " + accReqDto.getAccountType());
	    
	    Branch branch = iAccountDao.getBranchInfo(accReqDto.getBranchCode());
	    
	    if(!branch.getIsBranchOpen().equalsIgnoreCase("OPEN"))
	    	throw new NotFoundException("This Branch not Open :: "+accReqDto.getBranchCode());
	    
	    AccountCategories accCatId =iAccountDao.getAccountCatgoryByShortName(accReqDto.getAccountType());
	     
	    String new_accNo = iAccountDao.generateAccountNumber(branch.getBranchId(), accCatId.getAccCatId());
	    
	    logger.info("\nNew Generated Account No is: "+ new_accNo);
	    accReqDto.setBalance(
	    	(accReqDto.getBalance() == null || accReqDto.getBalance().compareTo(BigDecimal.ZERO) <= 0) 
	    			? BigDecimal.ZERO : accReqDto.getBalance()
	    );

	    accReqDto.setOverdraftLimit(
	    	(accReqDto.getOverdraftLimit() == null || accReqDto.getOverdraftLimit().compareTo(BigDecimal.ZERO) <= 0)
	    	        ? BigDecimal.ZERO : accReqDto.getOverdraftLimit()
	    );

	    
	    Account account =AccountMapper.INSTANCE.accountDtoToAccount(accReqDto);
	    account.setCustomerId(customer.customerId());
	    account.setBranchId(branch.getBranchId());
	    account.setAccountNumber(new_accNo);
	    
	    AccountInformation accInfo = AccountInformationMapper.INSTANCE.accInfoDtoToAccountInformation(accReqDto.getAccInformation());
	    accInfo.setCustomerId(customer.customerId());
	    accInfo.setAccount(account);
	    
	    if(account.getIsJointAccount() !=null && Character.toUpperCase(account.getIsJointAccount()) == 'Y' ) {
	    	if(accReqDto.getJointHolders() == null && accReqDto.getJointHolders().isEmpty())
	    		throw new NotFoundException("Joint Holder Account is Yes , But there No information Added");
	    	
	    	accReqDto.getJointHolders().forEach(jointAcc ->{
	    		jointAccHolderList.add(JointAccountHolder.builder()
	    				.addedDate(LocalDate.now())
	    				.customerId(customer.customerId())
	    				.holderType(jointAcc.getHolderType())
	    				.account(account)
	    				.build());

	    	});

	    }
	    account.setJointHolders(jointAccHolderList);
	    account.setAccountInfo(accInfo);
//	    AccountInformation accInfo = AccountInformation.builder()
//	    		.customerId(customer.customerId())
//	    		.nameTitle(new_accNo)
//	    		.accInfoName(null)
//	    		.accInfoGender(null)
//	    		.dateOfBirth(null)
//	    		.accInfoAmount(null)
//	    		.accInfoStartDate(LocalDate.now())
//	    		.accinfoStmtprnt(null)
//	    		.accInfoLoanNotice(null)
//	    		.accInfoDepNotice(null)
//	    		//.accInfoDispid(new_accNo)
//	    		.accInfoRemarks(new_accNo)
//	    		.build();
	    
//	    Account account = Account.builder()
//	    		.customerId(customer.customerId())
//	    		.branchId(branch.getBranchId())
//	    		.accountNumber(new_accNo)
//	    		.accountType(accReqDto.getAccountType())
//	    		.currencyCode(accReqDto.getCurrencyCode())     
//	    		.balance(accReqDto.getBalance())       
//	    		.status("ACTIVE")          
//	    		.openedDate(LocalDate.now())     
//	    		.branchId(accReqDto.getBranchId())           
//	    		.overdraftLimit(accReqDto.getOverdraftLimit())
//	    		.ibanNumber(accReqDto.getIbanNumber())      
//	    		.isJointAccount(accReqDto.getIsJointAccount()) 
//	    		.isChqYN(accReqDto.getIsChqYN())	    		
//	    		.build();
	    
	    Account accountDeatils = iAccountDao.saveAccount(account);	
	    AccountResponseDto response = new AccountResponseDto();
		return response;
	}

	@Override
	public AccountResponseDto getAccountDetails(Long accountId) {
		Account account = iAccountDao.getAccountInfoByAccountId(accountId, "Active");
		CustomerDto customer = customerClient.getCustomerDetails(account.getCustomerId());
		Branch branch = iAccountDao.getBranchInfo(account.getBranchId());
		AccountResponseDto accResDto = ObjectMapperUtils.map(account, AccountResponseDto.class);
		accResDto.setCifNo(customer.cifNo());
		accResDto.setBranchCode(branch.getBranchCode());
		accResDto.getAccountInfo().setAccountId(accountId);
		return accResDto;
	}

	@Override
	public Object getAllAccountForCutomer(String customerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object updateAccountMetaData(Long accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean activeDeactiveAccount(Long accountId) {
		Account account = iAccountDao.getAccountInfoByAccountId(accountId, "InActive");
		
		if(account.getStatus().equals("InActive")) 
			account.setStatus("Active");
		
		return	iAccountDao.saveAccount(account) !=null;
			
		
	}

	@Override
	public BigDecimal getCurrentBalanceOfAccount(Long accountId) {
		Account account = iAccountDao.getAccountInfoByAccountId(accountId, "Active");
		return account.getBalance();
	}

	@Override
	public Map<String, Object> getAllAccounts(int page, int size) {
		return iAccountDao.getAllAccounts(page,size);
	}

}
