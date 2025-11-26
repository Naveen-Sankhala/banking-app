package com.relx.banking.usermanagement.service;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.relx.banking.usermanagement.entity.Users;

/**
 * @author Naveen.Sankhala
 * Nov 26, 2025
 */
@Service
public interface IUserManagmentService {
	
	HashMap<String, Object> loadUserByUsername(String username, long branchId);

	LocalDateTime addUserLog(HashMap<String, Object> userLog);

	boolean isRefreshTokenExists(String remoteAddr, long userId, String token);

	boolean logout(String refreshToken);

	Users loadUserByUsername(String username);

	HashMap<String, Object> getAuthority(long userId, long barnchId);

}
