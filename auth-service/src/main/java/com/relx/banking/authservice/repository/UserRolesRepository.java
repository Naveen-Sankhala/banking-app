package com.relx.banking.authservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.relx.banking.authservice.entity.UserRoles;

/**
 * @author Naveen.Sankhala
 * Sep 24, 2025
 */
@Repository
public interface UserRolesRepository extends JpaRepository<UserRoles, Long> {

	List<UserRoles> findByUsersUserIdAndBranchId(long userId, long branchId);

	List<UserRoles> findByBranchId(Long branchId);

}
