package com.relx.banking.usermanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.relx.banking.usermanagement.entity.Users;


/**
 * @author Naveen.Sankhala
 * Nov 26, 2025
 */

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

	Optional<Users> findByUsernameAndBranchId(String username,long branchId);
	
	Optional<Users> findByUsername(String username);

}
