package com.relx.banking.authservice.dao;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.relx.banking.authservice.entity.UserLog;
import com.relx.banking.authservice.entity.Users;

/**
 * @author Naveen.Sankhala
 * Sep 24, 2025
 */
@Service
public interface IAuthorizationDao {
	
	HashMap<String, Object> loadUserByUsername(String username, Long branchId);

	boolean isRefreshTokenExists(String remoteAddr, long userId, String token);

	boolean markLogout(Long userId, LocalDateTime logoutTime);

	Users loadUserByUsername(String username);

	HashMap<String, Object> getAuthority(long userId, long branchId);

	List<UserLog> findByUserLogs(Long userId);

	boolean saveAndFlushUserLogs(UserLog userLogs);

	LocalDateTime getUserLastLoggedInTime(Long userId);

}
