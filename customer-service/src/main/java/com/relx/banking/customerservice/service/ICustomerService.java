package com.relx.banking.customerservice.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.relx.banking.customerservice.dto.CustomerAddressDto;
import com.relx.banking.customerservice.dto.CustomerRequestDto;
import com.relx.banking.customerservice.dto.CustomerResponseDto;

/**
 * @author Naveen Sankhala
 */
@Service
public interface ICustomerService {

	String createNewCustomer(CustomerRequestDto accountRequestDto);
	
	List<String> createBulkNewCustomer(List<CustomerRequestDto> customerReqDto);

	List<String> importAndCreateNewCustomer(MultipartFile custDetailsFile);

	CustomerResponseDto getCustomerDetails(String cifNo);
	
	CustomerResponseDto getCustomerDetails(Long customerId);

	boolean updateCustomerMetaData(CustomerRequestDto customerRequestDto);

	boolean activeDeactiveAccount(String cifNo);

	List<CustomerAddressDto> getAddresListOfCustomer(String cifNo);

	Map<String, Object> getAllCustomer(String custName, int page, int size);

	boolean verifyCustomer(String cifNo);

}
