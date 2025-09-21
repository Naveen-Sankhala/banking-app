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
		return GenderEnum.valueOf(value.toUpperCase());
	}
}
