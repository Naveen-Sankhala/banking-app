package com.relx.banking.usermanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.relx.banking.usermanagement.entity.UserRoles;

/**
 * @author Naveen.Sankhala
 * Nov 26, 2025
 */
@Repository
public interface UserRolesRepository extends JpaRepository<UserRoles, Long> {

	List<UserRoles> findByUsersUserIdAndBranchId(long userId, long branchId);

	List<UserRoles> findByBranchId(Long branchId);

}
