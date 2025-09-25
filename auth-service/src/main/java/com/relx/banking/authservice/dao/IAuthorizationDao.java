package com.relx.banking.authservice.dao;

import java.time.LocalDateTime;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.relx.banking.authservice.entity.Users;

/**
 * @author Naveen.Sankhala
 * Sep 24, 2025
 */
@Service
public interface IAuthorizationDao {

	LocalDateTime addUserLog(HashMap<String, Object> userLog);

	boolean isRefreshTokenExists(String remoteAddr, long userId, String token);

	boolean logout(String refreshToken);

	Users loadUserByUsername(String username);

	HashMap<String, Object> getAuthority(long userId, long hospitalId);

}
