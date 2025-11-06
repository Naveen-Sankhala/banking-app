package com.relx.banking.customerservice.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Naveen.Sankhala
 * Oct 8, 2025
 */
public class CustomerUtil {
	private static final Logger logger = LoggerFactory.getLogger(CustomerUtil.class);
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

	public static String getCellValue(Cell cell) {
		if (cell == null) return "";

		switch (cell.getCellType()) {
		case STRING:
			return cell.getStringCellValue().trim();
		case NUMERIC:
			if(DateUtil.isCellDateFormatted(cell)) {
				Date date = cell.getDateCellValue();
				LocalDate localDate = date.toInstant()
						.atZone(ZoneId.systemDefault())
						.toLocalDate();
				// Format date as "MM/dd/yyyy"
				return String.format("%02d-%02d-%04d",
						localDate.getDayOfMonth(),
						localDate.getMonthValue(),
						localDate.getYear());

			}else {
				double numericValue = cell.getNumericCellValue();
				return (numericValue == Math.floor(numericValue))
						? String.valueOf((long) numericValue)
								: String.valueOf(numericValue);
			}
		case BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
		case FORMULA:
			return cell.getCellFormula();
		case BLANK:
		default:
			return "";
		}
	}

	public static <T> T getMapObjectValue(Map<String, Object> map, String key, Class<T> type) {
		Object value = map.get(key);
		return (value != null && type.isInstance(value)) ? type.cast(value) : null;
	}

	public static <T> List<T> getMapObjectValue(String key, Object obj, Class<T> type) {
		if (obj == null) {
			logger.warn("⚠️ Object for key '{}' is null.", key);
			return List.of();
		}		
		if (obj instanceof List<?>) {
			List<?> rawList = (List<?>) obj;

			return rawList.stream()
					.map(element -> ObjectMapperUtils.convertValue(element, type))
					.collect(Collectors.toList());
		}

		return List.of();
	}

	private static void addJavaTimeConverters(ModelMapper mapper) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

		// LocalDate → String
		mapper.addConverter(ctx -> ctx.getSource() == null ? null :
			((LocalDate) ctx.getSource()).format(dateFormatter), LocalDate.class, String.class);

		// String → LocalDate
		mapper.addConverter(ctx -> ctx.getSource() == null ? null :
			LocalDate.parse((String) ctx.getSource(), dateFormatter), String.class, LocalDate.class);

		// LocalDateTime → String
		mapper.addConverter(ctx -> ctx.getSource() == null ? null :
			((LocalDateTime) ctx.getSource()).format(dateTimeFormatter), LocalDateTime.class, String.class);

		// String → LocalDateTime
		mapper.addConverter(ctx -> ctx.getSource() == null ? null :
			LocalDateTime.parse((String) ctx.getSource(), dateTimeFormatter), String.class, LocalDateTime.class);
	}
}
