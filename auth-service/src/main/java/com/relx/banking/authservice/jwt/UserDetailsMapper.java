package com.relx.banking.authservice.jwt;

import java.util.Collection;
import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;

import com.relx.banking.authservice.entity.Users;
import com.relx.banking.authservice.service.IAuthorizationService;
import com.relx.banking.authservice.util.UsernameNotFoundException;

/**
 * @author Naveen.Sankhala
 * Sep 27, 2025
 * If using ladap then mapped this class for UserDetails Mapper;
 */
//@Component
//public class UserDetailsMapper implements UserDetailsContextMapper{
//
//	private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//	@Autowired
//	private IAuthorizationService iAuthService;
//
//	@Override
//	public UserDetails mapUserFromContext(DirContextOperations ctx, String username,
//			Collection<? extends GrantedAuthority> authorities) {
//
//		Collection<GrantedAuthority> ga = new HashSet<>();
//		Users userDetails = iAuthService.loadUserByUsername(username);
//		logger.info("LDAP Login Success From DB...");
//
//		if(userDetails == null) {
//			throw new UsernameNotFoundException("Unauthorized access. Please contact web admin !");
//		}
//		/*try {
//			ga.add(new SimpleGrantedAuthority(userDetails.getUserRoleList().iterator().next().getMasRole().getRoleCode()));
//		}catch (Exception e) {
//			logger.error("Error Occured..",e);
//		}*/
//		return new User(username,"",ga);
//	}
//
//	@Override
//	public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {
//
//	}
//
//}
