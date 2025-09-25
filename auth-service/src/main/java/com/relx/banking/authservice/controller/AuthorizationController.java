package com.relx.banking.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.relx.banking.authservice.service.IAuthorizationService;

/**
 * @author Naveen.Sankhala
 * Sep 23, 2025
 */
@RestController
public class AuthorizationController {
	
	@Autowired
	private IAuthorizationService iAuthService;
	
}
