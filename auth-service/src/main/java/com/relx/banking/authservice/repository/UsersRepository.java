package com.relx.banking.authservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.relx.banking.authservice.entity.Users;

/**
 * @author Naveen.Sankhala
 * Sep 24, 2025
 */

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

	Optional<Users> findByUsername(String username);

}
