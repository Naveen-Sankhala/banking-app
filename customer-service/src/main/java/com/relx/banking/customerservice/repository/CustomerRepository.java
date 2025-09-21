package com.relx.banking.customerservice.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.relx.banking.customerservice.entity.Customer;

/**
 * @author Naveen.Sankhala
 * Jun 3, 2025
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
	
	Customer findByAadharNumberAndPanNumber(String aadharNumber,String panNumber);
	
	Optional<Customer> findByCustomerIdAndStatus(Long customerId, String status);

	Optional<Customer> findByCustomerIdentificationNoAndStatus(String custIdentificationNo, String status);

	//Page<Customer> findByStatusAndCustomerIdentificationNoIgnoreCaseContaining(String string, String title,Pageable paging);

	Page<Customer> findByStatusAndFirstNameIgnoreCaseContaining(String status, String custName, Pageable paging);
	
	Page<Customer> findByStatus(String status, Pageable paging);

	

	

}
