/**
 * 
 */
package com.relx.banking.customerservice.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * @author Naveen.Sankhala
 * Oct 8, 2025
 */
public class CustomerUtil {

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	public static LocalDate stringToLocalDate(String strDate) {
		return LocalDate.parse(strDate, formatter);

	}

	public static Character stringToCharcter(String str) {
		return  Optional.ofNullable(str)
				.filter(s -> !s.isEmpty())
				.map(s -> s.charAt(0))
				.orElse(null);
	}

	public static Long stringToLong(String str) {
		return  Optional.ofNullable(str)
				.filter(s -> s.matches("\\d+")) // ensure numeric
				.map(Long::parseLong)
				.orElse(0L);
	}

}
