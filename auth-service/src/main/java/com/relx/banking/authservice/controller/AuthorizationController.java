package com.relx.banking.authservice.controller;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.relx.banking.authservice.client.BankConfigApi;
import com.relx.banking.authservice.config.TokenService;
import com.relx.banking.authservice.entity.UserRoles;
import com.relx.banking.authservice.entity.Users;
import com.relx.banking.authservice.jwt.JwtTokenRequest;
import com.relx.banking.authservice.jwt.JwtTokenResponse;
import com.relx.banking.authservice.jwt.JwtTokenUtil;
import com.relx.banking.authservice.service.IAuthorizationService;
import com.relx.banking.authservice.util.ApiResponse;
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
	private IAuthorizationService iAuthorizationService;
	
	@Autowired
	private BankConfigApi bankConfigApi;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private MessageSource messageSource; 
	
//	@Value("${jwt.http.request.header}")
//	private String tokenHeader;
	
//	@Value("${ldap.auth}")
//	private boolean ldapAuth;
	
	@SuppressWarnings("unchecked")
	//@PostMapping(value = "${oauth2.get.token.uri}")
	@PostMapping("/oauth/token")
	@ApiOperation(value = "Generate JWT Tokens For Login.", notes = "Also returns a refresh token for retrieve new tokens")
	public ResponseEntity<?> createAuthenticationToken(HttpServletRequest request,
			@ApiParam("All Fields to be obtained. Cannot be empty.") 
			@Valid @RequestParam("username") String username ,
			@RequestParam("password") String password,
			@RequestParam("branchId") Long branchId)
					throws Exception { //@RequestBody JwtTokenRequest authenticationRequest

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
		
		BranchDetailsRecord branInfo = bankConfigApi.getBranchDetails(3L, null);

		
		final HashMap<String, Object> userDetailsMap = iAuthorizationService.loadUserByUsername(username,
				branchId);

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

			final String token = tokenService.createAccessToken((Users)userDetailsMap.get("user"),roles,branchId,sBranchName,sBranchType,"access");		
//			final String token = jwtTokenUtil.generateToken((Users)userDetailsMap.get("user"),roles,authenticationRequest.getBranchId(),sBranchName,sBranchType,"access");
//			final String refreshToken = jwtTokenUtil.generateToken((Users)userDetailsMap.get("user"),roles,authenticationRequest.getBranchId(),sBranchName,sBranchType,"refresh");

			HashMap<String, Object> userLog = new HashMap<String, Object>();
			userLog.put("userId", ((Users)userDetailsMap.get("user")).getUserId());
			userLog.put("ipAddress", remoteAddr);
			userLog.put("refreshToken", null);//refreshToken
			userLog.put("type", "access");
			LocalDateTime lastLoggedInTime=iAuthorizationService.addUserLog(userLog);
			return ResponseEntity.ok(HttpStatus.ACCEPTED);
			//return ResponseEntity.ok(new JwtTokenResponse(token, refreshToken, ((Users)userDetailsMap.get("user")).getUsername(),sBranchName, ((Users)userDetailsMap.get("user")).getLoginName(),lastLoggedInTime));
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false,messageSource.getMessage("6", null, LocaleContextHolder.getLocale())));
		}
	}




	
	@Autowired
	private IAuthorizationService iAuthService;
	
}
