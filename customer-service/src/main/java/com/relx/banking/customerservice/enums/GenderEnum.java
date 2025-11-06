package com.relx.banking.customerservice.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * @author Naveen.Sankhala
 * Sep 6, 2025
 */
public enum GenderEnum {
	MALE("MALE"),
	FEMALE("FEMALE"),
	OTHER("OTHER");

	private final String gender;

	GenderEnum(String gender) {
		this.gender = gender;
	}

	public String getGender() {
		return this.gender;
	}

	
	@JsonCreator
	public static GenderEnum fromString(String value) {
		if (value == null) 
			return null;
		
		String normalized = value.trim().toUpperCase();
	    for (GenderEnum gender : GenderEnum.values()) {
	        if (gender.name().equalsIgnoreCase(normalized) || gender.getGender().equalsIgnoreCase(normalized)) {
	        	 System.out.println("Find gender : " + gender);
	        	return gender;
	        }
	    }
	    System.err.println("Unknown gender : " + value);
	    return null;
	}
}
