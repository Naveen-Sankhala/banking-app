package com.relx.banking.authservice.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.relx.banking.authservice.client.BankConfigApi;
import com.relx.banking.authservice.entity.UserRoles;
import com.relx.banking.authservice.entity.Users;
import com.relx.banking.authservice.oauth2.ClaimsData;
import com.relx.banking.authservice.oauth2.JwtTokenResponse;
import com.relx.banking.authservice.oauth2.OAuthTokenUtill;
import com.relx.banking.authservice.service.IAuthorizationService;
import com.relx.banking.authservice.util.ApiResponse;
import com.relx.banking.authservice.util.AuthenticationException;
import com.relx.banking.commonrecord.BranchDetailsRecord;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

/**
 * @author Naveen.Sankhala
 * Sep 23, 2025
 */
@RestController
@RequestMapping("oauth")
@CrossOrigin(origins = "${corss.url}")
@Tag(name = "authorization-controller", description = "Set of endpoints for Login in Application.")
public class AuthorizationController {

	private final static Logger logger = LoggerFactory.getLogger(AuthorizationController.class);

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	@Autowired
	private IAuthorizationService iAuthService;

	@Autowired
	private BankConfigApi bankConfigApi;

	@Autowired
	private OAuthTokenUtill authTokenUtill;

	@Autowired
	private MessageSource messageSource; 

	//	@Value("${jwt.http.request.header}")
	//	private String tokenHeader;

	//	@Value("${ldap.auth}")
	//	private boolean ldapAuth;

	@SuppressWarnings("unchecked")
	@PostMapping(value = "${spring.security.oauth2.get.token-uri}")
	@ApiOperation(value = "Generate JWT Tokens For Login.", notes = "Also returns a refresh token for retrieve new tokens")
	public ResponseEntity<?> createAuthenticationToken(HttpServletRequest request,
			@ApiParam("All Fields to be obtained. Cannot be empty.") 
	@Valid @RequestParam("username") String username ,
	@RequestParam("password") String password,
	@RequestParam("branchId") Long branchId) throws Exception { 

		String remoteAddr = "";
		if (request != null) {
			remoteAddr = request.getHeader("X-FORWARDED-FOR");
			if (remoteAddr == null || "".equals(remoteAddr)) {
				remoteAddr = request.getRemoteAddr();
			}
		}
		logger.info("=====>> Login Request Comming From :: "+remoteAddr);
		//		if(ldapAuth) {
		//			try {
		//				authenticate(authenticationRequest.getUsername(), new String(Base64.getDecoder().decode(authenticationRequest.getPassword()),StandardCharsets.UTF_8));
		//			}catch(Exception e) {
		//				throw new AuthenticationException(messageSource.getMessage("13", null, LocaleContextHolder.getLocale()), e);
		//			}
		//		}

		BranchDetailsRecord branInfo = bankConfigApi.getBranchDetails(branchId, null);

		final HashMap<String, Object> userDetailsMap = iAuthService.loadUserByUsername(username,branchId);

		if(userDetailsMap!=null && userDetailsMap.containsKey("user") && userDetailsMap.containsKey("userRoles")) {

			List<UserRoles> userRoles = (List<UserRoles>) userDetailsMap.get("userRoles");

			List<String> roles = Optional.ofNullable(userRoles)
					.orElse(Collections.emptyList())
					.stream()
					.map(role -> role.getMasRole().getRoleCode())
					.collect(Collectors.toList());

			boolean hasAccess = userRoles.stream()
					.anyMatch(role -> role.getBranchId().equals(branInfo.branchId()));

			final String sBranchName = hasAccess ? branInfo.branchName() : null;
			final String sBranchType = hasAccess ? branInfo.branchType() : null;

			logger.info("Branch Access → ID: {}, Name: {}, Type: {}", branInfo.branchId(), sBranchName, sBranchType);

			final String token = authTokenUtill.generateAccessToken((Users)userDetailsMap.get("user"),roles,branchId,sBranchName,sBranchType,"access");		
			final String refreshToken = authTokenUtill.generateAccessToken((Users)userDetailsMap.get("user"),roles,branchId,sBranchName,sBranchType,"refresh");
			HashMap<String, Object> userLog = new HashMap<String, Object>();
			userLog.put("userId", ((Users)userDetailsMap.get("user")).getUserId());
			userLog.put("ipAddress", remoteAddr);
			userLog.put("refreshToken", refreshToken);
			userLog.put("type", "access");
			LocalDateTime lastLoggedInTime=iAuthService.addUserLog(userLog);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new JwtTokenResponse(token, refreshToken, ((Users)userDetailsMap.get("user")).getUsername(),sBranchName, ((Users)userDetailsMap.get("user")).getLoginName(),lastLoggedInTime));
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false,messageSource.getMessage("6", null, LocaleContextHolder.getLocale())));
		}
	}


	@PostMapping(value = "${spring.security.oauth2.refresh.token-uri}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ApiOperation(value = "Generate Refresh Tokens For Login.", notes = " Returns Access and Refresh token..")
	public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request,
			@ApiParam("refreshToken Field to be obtained. Cannot be empty.") 
	@Valid @RequestParam("refreshToken") String refToken) throws Exception {

		String remoteAddr = "";
		if (request != null) {
			remoteAddr = request.getHeader("X-FORWARDED-FOR");
			if (remoteAddr == null || "".equals(remoteAddr)) {
				remoteAddr = request.getRemoteAddr();
			}
		}
		//String authToken = request.getHeader(tokenHeader);
		if(refToken!=null && !refToken.equals("")) {
			final String token = refToken;
			boolean isTokenExpired = false;

			try {
				isTokenExpired = authTokenUtill.isTokenExpired(token);

			} catch (IllegalArgumentException  e) {
				isTokenExpired=true;
				throw new AuthenticationException(messageSource.getMessage("10", null, LocaleContextHolder.getLocale()), e);
			} 

			if(!isTokenExpired) {

				ClaimsData claimsData = authTokenUtill.parseToken(token);

				boolean isRefreshTokenExists = iAuthService.isRefreshTokenExists(remoteAddr,claimsData.getUserId(),token);

				if(isRefreshTokenExists) {
					final String accessToken = authTokenUtill.refreshToken(token,"access");
					final String refreshToken = authTokenUtill.refreshToken(token,"refresh");


					HashMap<String, Object> userLog = new HashMap<String, Object>();
					userLog.put("userId", claimsData.getUserId());
					userLog.put("refreshToken", refreshToken);
					userLog.put("type", "refresh");
					LocalDateTime lastLoggedInTime=iAuthService.addUserLog(userLog);

					return ResponseEntity.status(HttpStatus.ACCEPTED).body(new JwtTokenResponse(accessToken,refreshToken,claimsData.getUserName(),claimsData.getBranchName(),claimsData.getLoginName(),lastLoggedInTime));
				}
			}
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false,messageSource.getMessage("10", null, LocaleContextHolder.getLocale())));
	}


	private void authenticate(String username, String password) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			//authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new AuthenticationException("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new AuthenticationException(messageSource.getMessage("9", null, LocaleContextHolder.getLocale()), e);
		}
	}

	@GetMapping(value = "/getMenus")
	@ApiOperation(value = "Get Menu List", notes = " Get Menu List According to Login User.")
	ResponseEntity<?> getMenus(){
		//List<String> roles=iAuthenticationFacade.getUserRoles();
		List<String> roles= new ArrayList<String>();
		return ResponseEntity.ok(iAuthService.getMenus(roles));
	}

	@PostMapping(value = "${spring.security.oauth2.logout.token-uri}")
	@ApiOperation(value = "LogOut Request.", notes = " Also Destroyed the refresh token..")
	ResponseEntity<?> logout(
			@ApiParam("refreshToken Field to be obtained. Cannot be empty.") 
			@Valid @RequestParam("user-id") Long userId){
		boolean result= iAuthService.markLogout(userId);
		if(result)
			return ResponseEntity.ok(new ApiResponse(result,messageSource.getMessage("8", null, LocaleContextHolder.getLocale())));
		else
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false,messageSource.getMessage("2", null, LocaleContextHolder.getLocale())));
	}

}
