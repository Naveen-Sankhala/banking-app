package com.relx.banking.bankconfig.controller;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Naveen.Sankhala
 * Sep 18, 2025
 */

@RefreshScope
@RestController
@RequestMapping("/config")
public class ConfigController {

}
