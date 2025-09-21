package com.relx.banking.customerservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.relx.banking.customerservice.entity.Address;
import com.relx.banking.customerservice.enums.AddressType;

/**
 * @author Naveen.Sankhala
 * Sep 9, 2025
 */
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

	Optional<Address> findByAddressIdAndAddressType(long addressId, AddressType addressType);

	List<Address> findByCustomerCustomerId(long customerId);

}
