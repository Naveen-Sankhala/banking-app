package com.relx.banking.eventchanged;

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

	private static final long serialVersionUID = -165383144946764826L;
	
	private String key;
    private Object value;
}
