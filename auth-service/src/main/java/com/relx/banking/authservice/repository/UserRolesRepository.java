package com.relx.banking.authservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.relx.banking.authservice.entity.UserRoles;

/**
 * @author Naveen.Sankhala
 * Sep 24, 2025
 */
@Repository
public interface UserRolesRepository extends JpaRepository<UserRoles, Long> {

}
