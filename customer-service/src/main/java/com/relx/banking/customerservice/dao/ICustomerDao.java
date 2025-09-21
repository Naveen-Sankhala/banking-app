package com.relx.banking.customerservice.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.relx.banking.customerservice.entity.Address;
import com.relx.banking.customerservice.entity.Customer;
import com.relx.banking.customerservice.enums.AddressType;


/**
 * @author Naveen.Sankhala
 * Jun 3, 2025
 */
@Service
public interface ICustomerDao {

	Customer saveAndFlushCustomer(Customer customer);

	Customer saveCustomer(Customer customer);

	Customer findCustomerByAadharAndPanNo(String aadharNumber,String panNumber);

	Customer getCustomerDetails(String custIdentificationNo, String status);
	
	Customer getCustomerDetails(Long customerId, String status);

	Address getAddressOfCustomer(long addressId,AddressType addressType);

	List<Address> getAddresListOfCustomer(long cudtomerId);

	Map<String, Object> getAllCustomer(String title, int page, int size);

	

}
