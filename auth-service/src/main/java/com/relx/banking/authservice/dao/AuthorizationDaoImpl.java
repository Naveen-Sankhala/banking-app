package com.relx.banking.authservice.dao;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.relx.banking.authservice.entity.UserLog;
import com.relx.banking.authservice.entity.UserRoles;
import com.relx.banking.authservice.entity.Users;
import com.relx.banking.authservice.repository.UserLogRepository;
import com.relx.banking.authservice.repository.UserRolesRepository;
import com.relx.banking.authservice.repository.UsersRepository;
import com.relx.banking.authservice.util.EntityNotFoundException;
import com.relx.banking.authservice.util.UsernameNotFoundException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * @author Naveen.Sankhala
 * Sep 24, 2025
 */

@Service
public class AuthorizationDaoImpl implements IAuthorizationDao {
	
//	@PersistenceContext
//	private EntityManager entityManager;
	
	@Autowired
	private UsersRepository usersJpaRepo;
	
	@Autowired
	private UserLogRepository userLogRepo;
	
	@Autowired
	private UserRolesRepository userRolesRepo;
	
	public HashMap<String, Object> loadUserByUsername(String username, Long branchId) throws UsernameNotFoundException {
		HashMap<String, Object> userDetailsMap = new HashMap<String, Object>();
		
		Optional<Users> user = usersJpaRepo.findByUsername(username);

		if (!user.isPresent()) {
			throw new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'", username));
		}
		List<UserRoles> userRoles=userRolesRepo.findByUsersUserIdAndBranchId(user.get().getUserId(),branchId);

		userDetailsMap.put("user", user.get());
		userDetailsMap.put("userRoles", userRoles);

		return userDetailsMap;
	}

	/*
	 * @Override public List<Menus> getMenus(List<String> roles) {
	 * log.info("::::::: Get Menus For Roles ::::::: "+roles.toString()); return
	 * entityManager.createNamedQuery("FIND_MENUS",Menus.class)
	 * .setParameter("roleCode", roles)
	 * .setHint(QueryHints.HINT_PASS_DISTINCT_THROUGH, false) .getResultList(); }
	 */
	
	@Override
	public List<UserLog> findByUserLogs(Long userId) {
		return userLogRepo.findByUserIdOrderByUserLogIdDesc(userId);
	}

	@Override
	public boolean saveAndFlushUserLogs(UserLog userLogs) {
		return userLogRepo.saveAndFlush(userLogs) != null;
	}

	@Override
	public LocalDateTime getUserLastLoggedInTime(Long userId) {
		return userLogRepo.getLastLoggedInTime(userId);
	}
	

	@Override
	public boolean isRefreshTokenExists(String remoteAddr, long userId, String token) {
		
		//Optional<UserLog> findFirst = userLogJpaRepo.findByIpAddressAndUserIdAndRefreshToken(remoteAddr,userId,token).stream()
		//		.filter(log -> log.getRefreshToken().equals(token)).findFirst();
		
		Optional<UserLog> findFirst = userLogRepo.findByUserIdOrderByUserLogIdDesc(userId).stream()
				.filter(log -> log.getUserId().equals(userId)).findFirst();
		
		if(findFirst.get().getIpAddress().equals(remoteAddr) && findFirst.get().getRefreshToken().equals(token))
			return true;

		return false;
	}

	@Override
	public boolean logout(String refreshToken) {
		UserLog userLogs = userLogRepo.findByRefreshToken(refreshToken);
		userLogs.setRefreshToken(null);
		userLogs.setIsLoggedIn('N');;
		userLogs.setLoggedOutTime(LocalDateTime.now());
		return userLogRepo.save(userLogs) != null;
	}

	@Override
	public Users loadUserByUsername(String username) {
		Optional<Users> findFirst = usersJpaRepo.findByUsername(username).stream()
				.filter(user -> user.getUsername().equals(username)).findFirst();

		if (!findFirst.isPresent()) {
			throw new UsernameNotFoundException(String.format("USER_NOT_FOUND '%s'.", username));
		}
		return findFirst.get();
	}
	
	@Override
	public HashMap<String, Object> getAuthority(long userId, long hospitalId) {
		
		HashMap<String, Object> authorities = new HashMap<String, Object>();
		
		//List<String> userRoles= userRolesJpaRepo.findByUsersUserIdAndHospitalId(userId,hospitalId)
		//		.stream().map(map -> map.getMasRole().getRoleCode()).collect(Collectors.toList());
		
		Optional<UserLog> findFirst = userLogRepo.findByUserIdOrderByUserLogIdDesc(userId).stream()
				.filter(log -> log.getUserId().equals(userId)).findFirst();
		
		if (!findFirst.isPresent()) {
			throw new EntityNotFoundException(UserLog.class, "UserId", String.valueOf(userId));
		}
		
		//authorities.put("userRoles", userRoles);
		authorities.put("refToken", findFirst.get().getRefreshToken());
		return authorities;
	}

	

}
