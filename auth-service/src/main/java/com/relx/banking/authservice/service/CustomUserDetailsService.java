package com.relx.banking.authservice.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.relx.banking.authservice.dao.IAuthorizationDao;
import com.relx.banking.authservice.entity.Users;
import com.relx.banking.authservice.util.UsernameNotFoundException;

import lombok.RequiredArgsConstructor;

/**
 @author Naveen.Sankhala
 * Nov 19, 2025
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final IAuthorizationDao iAuthorizationDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Example: modify to call your iAuthorizationDao
        HashMap<String, Object> userDetailsMap = iAuthorizationDao.loadUserByUsername(username, 1L); // dummy branchId
        if (userDetailsMap == null || !userDetailsMap.containsKey("user")) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        Users user = (Users) userDetailsMap.get("user");
        List<String> roles = Optional.ofNullable((List<String>) userDetailsMap.get("userRoles"))
                .orElse(Collections.emptyList());

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(null) //user.getPassword()
                .authorities(roles.toArray(new String[0]))
                .build();
    }
}
