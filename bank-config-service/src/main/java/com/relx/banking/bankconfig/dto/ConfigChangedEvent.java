package com.relx.banking.bankconfig.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Naveen.Sankhala
 * Sep 19, 2025
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfigChangedEvent implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String key;   // e.g. "bankDate", "bankConfig"
    private Object value; // the updated object (JSON serialized)
}
