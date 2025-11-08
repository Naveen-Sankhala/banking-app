package com.relx.banking.loanservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Naveen.Sankhala
 * Nov 8, 2025
 */
@RestController
@RequestMapping
@CrossOrigin
public class LoanController {
	public static final Logger logger =  LoggerFactory.getLogger(LoanController.class);

}
