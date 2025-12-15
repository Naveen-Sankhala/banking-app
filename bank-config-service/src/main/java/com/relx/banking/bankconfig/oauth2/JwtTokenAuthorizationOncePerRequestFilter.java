package com.relx.banking.bankconfig.oauth2;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.relx.banking.bankconfig.client.UserManagmentApi;
import com.relx.banking.commonsecurity.Auth2TokenUtil;
import com.relx.banking.commonsecurity.ClaimsData;
import com.relx.banking.util.exception.AuthenticationException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Naveen.Sankhala
 * Nov 30, 2025
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenAuthorizationOncePerRequestFilter extends OncePerRequestFilter{

	private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
	
	private final UserManagmentApi userManageApi;
	
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {


		log.debug("Authentication Request For '{}'", request.getRequestURL());

		final String requestTokenHeader = request.getHeader(AUTH_HEADER);

		String reqURI = request.getRequestURI();

		if(reqURI.equals("/config/")) { 
			//|| reqURI.contains("/awamss/refresh") ||reqURI.contains("/awamss/logout")
			logger.info("Filter ByPass For :: "+reqURI);
		}else {
			String username = null;
			String jwtToken = null;
			if (requestTokenHeader != null && requestTokenHeader.startsWith(BEARER_PREFIX)) {
				jwtToken = requestTokenHeader.substring(7);
				try {
    				username = Auth2TokenUtil.getUsernameFromToken(jwtToken);
    			} catch (IllegalArgumentException e) {
    				logger.error("JWT_TOKEN_UNABLE_TO_GET_USERNAME", e);
    				throw new AuthenticationException("JWT_TOKEN_UNABLE_TO_GET_USERNAME", e);
    			} 
			} else {
				logger.warn("JWT_TOKEN_DOES_NOT_START_WITH_BEARER_STRING");
			}
			log.debug("JWT_TOKEN_USERNAME_VALUE '{}'", username);
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				
    			ClaimsData claimsData =Auth2TokenUtil.parseToken(jwtToken);
    			//UserDetails userDetails = this.jwtInMemoryUserDetailsService.loadUserByUsername(username);

    			HashMap<String, Object> validMap = userManageApi.getAuthority(claimsData.getUserId(), claimsData.getBranchId());
    			
    			@SuppressWarnings("unchecked")
				List<String> userRoles = (List<String>) validMap.get("userRoles");

    			if(Auth2TokenUtil.getIssuedAtDateFromToken((String) validMap.get("refToken")).after(Auth2TokenUtil.getIssuedAtDateFromToken(jwtToken))) {
    				throw new AuthenticationException("Invalid Session Please Login Again...!!!");
    			}

    			
    			List<GrantedAuthority> authorities= userRoles.stream().filter(Objects::nonNull)
    					.map(role-> new SimpleGrantedAuthority("ROLE_"+role))
    					.collect(Collectors.toList());

    			ClaimsData claimsDataNew = ClaimsData.builder()
    					
    					.UserId(claimsData.getUserId())
    					.UserName(claimsData.getUserName())
    					.LoginName(claimsData.getLoginName())
    					.BranchId(claimsData.getBranchId())
    					.BranchName(claimsData.getBranchName())
    					.Roles(userRoles)
    					.BranchType(claimsData.getBranchType())
    					.build();
    			
    			if (Auth2TokenUtil.validateToken(jwtToken, claimsDataNew)) {
    				//UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(claimsDataNew, null, authorities);
    				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    			}
				 
			}

		}
		filterChain.doFilter(request, response);
	}

}
