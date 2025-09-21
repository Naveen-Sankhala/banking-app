package com.relx.banking.commondto;

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
public class ConfigChangedEvent {

	private String key;
    private Object value;
}
