package com.relx.banking.usermanagement.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.relx.banking.usermanagement.dao.IUserManagmentDao;
import com.relx.banking.usermanagement.entity.UserLog;
import com.relx.banking.usermanagement.entity.Users;
import com.relx.banking.usermanagement.util.exception.AuthenticationException;
import com.relx.banking.usermanagement.util.exception.EntityNotFoundException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * @author Naveen.Sankhala
 * Nov 26, 2025
 */
@Service
@RequiredArgsConstructor
public class UserManagmentServiceImpl implements IUserManagmentService {
	
private final static Logger logger = LoggerFactory.getLogger(UserManagmentServiceImpl.class);
	
	private final IUserManagmentDao iUserManagmentDao;
	
	@Override
	public HashMap<String, Object> loadUserByUsername(String username, long branchId){
		HashMap<String, Object> userDetailsMap = null;
		try {
			userDetailsMap= iUserManagmentDao.loadUserByUsername(username, Long.valueOf(branchId));
		}catch (Exception e) {
			throw new AuthenticationException(String.format("USER_NOT_FOUND '%s'", username),e);
		}
		return userDetailsMap;
	}

//	@Override
//	public List<Menus> getMenus(List<String> roles) {
//		List<Menus> menuList= iUserManagmentDao.getMenus(roles);
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
			
			Optional<UserLog> findFirst = iUserManagmentDao.findByUserLogs((Long)userLog.get("userId")).stream()
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

		iUserManagmentDao.saveAndFlushUserLogs(userLogs);
		LocalDateTime lastLoggedInTime = iUserManagmentDao.getUserLastLoggedInTime(userLogs.getUserId());
		lastLoggedInTime=lastLoggedInTime!=null?lastLoggedInTime:LocalDateTime.now();
		return lastLoggedInTime;
	
	}

	@Override
	public boolean isRefreshTokenExists(String remoteAddr, long userId, String token) {
		boolean result=false;
		try {
			result=iUserManagmentDao.isRefreshTokenExists(remoteAddr,userId,token);
		}catch (Exception e) {
			logger.error("Error Occured : ", e);
		}
		return result;
	}

	@Override
	public boolean logout(String refreshToken) {
		return iUserManagmentDao.logout(refreshToken);
	}

	@Override
	public Users loadUserByUsername(String username) {
		Users users=null;
		try {
			users= iUserManagmentDao.loadUserByUsername(username);
		}catch (Exception e) {
			logger.error("Error Occured : ", e);
		}
		return users;
	}

	@Override
	public HashMap<String, Object> getAuthority(long userId, long barnchId) {
		return iUserManagmentDao.getAuthority(userId, barnchId);
	}

}
