/**
 * 
 */
package com.relx.banking.customerservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.relx.banking.customerservice.entity.Customer;
import com.relx.banking.customerservice.entity.CustomerDetails;

/**
 * @author Naveen.Sankhala
 * Jan 9, 2026
 */
@Repository
public interface CustomerDetailsRepository extends JpaRepository<CustomerDetails, Long> {

	//Customer findByAadharNumberAndPanNumber(String aadharNumber,String panNumber);

	//@Query("SELECT c FROM Customer c WHERE c.aadharNumber IN :aadharNumbers AND c.panNumber IN :panNumbers")
	//List<Customer> findByAadharAndPanIn(@Param("aadharNumbers") List<String> aadharNumbers,@Param("panNumbers") List<String> panNumbers);

}
