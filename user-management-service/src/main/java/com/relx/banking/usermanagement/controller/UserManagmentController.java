package com.relx.banking.usermanagement.controller;

import java.util.HashMap;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.relx.banking.usermanagement.service.IUserManagmentService;
import com.relx.banking.usermanagement.util.exception.ApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * @author Naveen.Sankhala
 * Nov 26,2025
 */
@RestController
@RequestMapping("")
@CrossOrigin(origins = "${cors.url}")
@Tag(name = "authorization-controller", description = "Set of endpoints for Login in Application.")
public class UserManagmentController {

	private final static Logger logger = LoggerFactory.getLogger(UserManagmentController.class);
	
	@Autowired
	private MessageSource messageSource; 
	
	@Autowired
	private IUserManagmentService iUserManagmentService;

	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}
	
	@GetMapping("users")
	public ResponseEntity<?> findUserByUsername(@RequestParam("user-name")String userName,@RequestParam("branchId")Long branchId){
		logger.info("=====>> Login Request Comming From :: ");
		final HashMap<String, Object> userDetailsMap = iUserManagmentService.loadUserByUsername(userName,branchId);
		
		if(userDetailsMap!=null && userDetailsMap.containsKey("user") && userDetailsMap.containsKey("userRoles")) {
			return ResponseEntity.status(HttpStatus.FOUND).body(userDetailsMap);
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false,messageSource.getMessage("6", null, LocaleContextHolder.getLocale())));
		}
	}
	
	
	@GetMapping("authority")
	ResponseEntity<?> getAuthority(@RequestParam("user-id")Long userId,@RequestParam("branch-id")Long barnchId){
		final HashMap<String, Object> userAuthority = iUserManagmentService.getAuthority(userId,barnchId);
		
		if(userAuthority!=null) {
			return ResponseEntity.status(HttpStatus.OK).body(userAuthority);
		}else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(false,messageSource.getMessage("6", null, LocaleContextHolder.getLocale())));
		}
	} 

}
