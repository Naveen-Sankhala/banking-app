package com.relx.banking.customerservice.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.relx.banking.customerservice.entity.Address;
import com.relx.banking.customerservice.entity.Customer;
import com.relx.banking.customerservice.enums.AddressType;
import com.relx.banking.customerservice.repository.AddressRepository;
import com.relx.banking.customerservice.repository.CustomerRepository;
import com.relx.banking.customerservice.util.CustomerConstant;

import jakarta.persistence.EntityNotFoundException;

/**
 * @author Naveen.Sankhala
 * Jun 3, 2025
 */
@Service
public class CustomerDaoImpl implements ICustomerDao {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private AddressRepository addressRepo;

	@Override
	@Transactional
	public Customer saveAndFlushCustomer(Customer customer) {
		return customerRepository.saveAndFlush(customer);
	}

	@Override
	@Transactional
	public Customer saveCustomer(Customer customer) {
		return customerRepository.save(customer);
	}
	
	@Override
	public Customer findCustomerByAadharAndPanNo(String aadharNumber,String panNumber) {
		return customerRepository.findByAadharNumberAndPanNumber( aadharNumber, panNumber);
	}

	@Override
	public List<Customer> findByAadharAndPanIn(List<String> aadharNumbers, List<String> panNumbers) {
		return customerRepository.findByAadharAndPanIn(aadharNumbers, panNumbers);
	}
	
	@Override
	public Customer getCustomerDetails(String custIdentificationNo, String status) {
		return customerRepository.findByCustomerIdentificationNoAndStatus(custIdentificationNo,status)
				.orElseThrow(()-> new EntityNotFoundException("Customer not found for this CIF No " + custIdentificationNo));
	}
	
	@Override
	public Customer getCustomerDetails(Long customerId, String status) {
		return customerRepository.findByCustomerIdAndStatus(customerId,status)
				.orElseThrow(()-> new EntityNotFoundException("Customer not found"));
	}

	@Override
	public Address getAddressOfCustomer(long addressId,AddressType addressType) {
		return addressRepo.findByAddressIdAndAddressType( addressId,addressType)
				.orElseThrow(()-> new EntityNotFoundException("Address not found for ID " + addressId  + " and type " + addressType));
	}
	
	@Override
	public List<Address> getAddresListOfCustomer(long customerId) {
		return addressRepo.findByCustomerCustomerId(customerId);
	}
	
	@Override
	public Map<String, Object> getAllCustomer(String custName, int page, int size){
		Map<String, Object> response = new HashMap<>();
		
		List<Customer> customer = new ArrayList<Customer>();
		Pageable paging = PageRequest.of(page, size, Sort.by(CustomerConstant.CIF_NO));

		Page<Customer> pageCustomer;
		
		if (custName == null)
			pageCustomer = customerRepository.findByStatus("Active",paging);
		else
			pageCustomer = customerRepository.findByStatusAndFirstNameIgnoreCaseContaining("Active",custName, paging);

		customer = pageCustomer.getContent();
		
		response.put("customer", customer);
		response.put(CustomerConstant.CURRENTPAGE, pageCustomer.getNumber());
		response.put(CustomerConstant.TOTALITEMS, pageCustomer.getTotalElements());
		response.put(CustomerConstant.TOTALPAGES, pageCustomer.getTotalPages());
		response.put(CustomerConstant.HASNEXT, pageCustomer.hasNext());
		response.put(CustomerConstant.HASPREVIOUS,pageCustomer.hasPrevious());
		return response;
	}

}
