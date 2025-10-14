package com.relx.banking.authservice.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.relx.banking.authservice.dao.IAuthorizationDao;
import com.relx.banking.authservice.entity.UserLog;
import com.relx.banking.authservice.entity.Users;
import com.relx.banking.authservice.util.AuthenticationException;
import com.relx.banking.authservice.util.EntityNotFoundException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * @author Naveen.Sankhala
 * Sep 23, 2025
 */
@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements IAuthorizationService {
	
	private final static Logger logger = LoggerFactory.getLogger(AuthorizationServiceImpl.class);
	
	private final IAuthorizationDao iAuthorizationDao;
	
	@Override
	public HashMap<String, Object> loadUserByUsername(String username, long branchId){
		HashMap<String, Object> userDetailsMap = null;
		try {
			userDetailsMap= iAuthorizationDao.loadUserByUsername(username, Long.valueOf(branchId));
		}catch (Exception e) {
			throw new AuthenticationException(String.format("USER_NOT_FOUND '%s'", username),e);
		}
		return userDetailsMap;
	}

//	@Override
//	public List<Menus> getMenus(List<String> roles) {
//		List<Menus> menuList= iAuthorizationDao.getMenus(roles);
//		if(menuList==null || menuList.isEmpty())
//			throw new EntityNotFoundException(Menus.class, "Menus", roles.toString());
//
//		return menuList;
//	}

	@Override
	@Transactional
	public LocalDateTime addUserLog(HashMap<String, Object> userLog) {

		UserLog userLogs=null;
		if(userLog.containsKey("type") && ((String)userLog.get("type")).equalsIgnoreCase("refresh")) {
			
			Optional<UserLog> findFirst = iAuthorizationDao.findByUserLogs((Long)userLog.get("userId")).stream()
					.filter(log -> log.getUserId().equals((Long)userLog.get("userId"))).findFirst();
			if (!findFirst.isPresent()) {
				throw new EntityNotFoundException(UserLog.class, "UserId", String.valueOf((Long)userLog.get("userId")));
			}
			userLogs=findFirst.get();
		}
		else {
			userLogs=new UserLog();

			if(userLog.containsKey("userId"))
				userLogs.setUserId((Long) userLog.get("userId"));

			if(userLog.containsKey("ipAddress"))
				userLogs.setIpAddress((String) userLog.get("ipAddress"));

			userLogs.setIsLoggedIn('Y');
		}

		if(userLog.containsKey("refreshToken"))
			userLogs.setRefreshToken((String) userLog.get("refreshToken"));

		iAuthorizationDao.saveAndFlushUserLogs(userLogs);
		LocalDateTime lastLoggedInTime = iAuthorizationDao.getUserLastLoggedInTime(userLogs.getUserId());
		lastLoggedInTime=lastLoggedInTime!=null?lastLoggedInTime:LocalDateTime.now();
		return lastLoggedInTime;
	
	}

	@Override
	public boolean isRefreshTokenExists(String remoteAddr, long userId, String token) {
		boolean result=false;
		try {
			result=iAuthorizationDao.isRefreshTokenExists(remoteAddr,userId,token);
		}catch (Exception e) {
			logger.error("Error Occured : ", e);
		}
		return result;
	}

	@Override
	public boolean logout(String refreshToken) {
		return iAuthorizationDao.logout(refreshToken);
	}

	@Override
	public Users loadUserByUsername(String username) {
		Users users=null;
		try {
			users= iAuthorizationDao.loadUserByUsername(username);
		}catch (Exception e) {
			logger.error("Error Occured : ", e);
		}
		return users;
	}

	@Override
	public HashMap<String, Object> getAuthority(long userId, long barnchId) {
		return iAuthorizationDao.getAuthority(userId, barnchId);
	}


}
