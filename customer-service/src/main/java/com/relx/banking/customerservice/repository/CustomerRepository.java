package com.relx.banking.customerservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.relx.banking.customerservice.entity.Customer;

/**
 * @author Naveen.Sankhala
 * Jun 3, 2025
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
	
	Customer findByAadharNumberAndPanNumber(String aadharNumber,String panNumber);
	
	@Query("SELECT c FROM Customer c WHERE c.aadharNumber IN :aadharNumbers AND c.panNumber IN :panNumbers")
	List<Customer> findByAadharAndPanIn(@Param("aadharNumbers") List<String> aadharNumbers,@Param("panNumbers") List<String> panNumbers);
	
	Optional<Customer> findByCustomerIdAndStatus(Long customerId, String status);

	Optional<Customer> findByCustomerIdentificationNoAndStatus(String custIdentificationNo, String status);

	//Page<Customer> findByStatusAndCustomerIdentificationNoIgnoreCaseContaining(String string, String title,Pageable paging);

	Page<Customer> findByStatusAndFirstNameIgnoreCaseContaining(String status, String custName, Pageable paging);
	
	Page<Customer> findByStatus(String status, Pageable paging);

	

	

}
