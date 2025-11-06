package com.relx.banking.accountservice.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * @author Naveen.Sankhala
 */
public class ObjectMapperUtils {
	private static final Logger logger = LoggerFactory.getLogger(ObjectMapperUtils.class);
	private static ModelMapper modelMapper = new ModelMapper();
	private static final ObjectMapper objectMapper = new ObjectMapper();
	private static ObjectWriter objectWriter ;;

	/**
	 * Model mapper property setting are specified in the following block.
	 * Default property matching strategy is set to Strict see {@link MatchingStrategies}
	 * Custom mappings are added using {@link ModelMapper#addMappings(PropertyMap)}
	 */
	static {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
		.setMatchingStrategy(MatchingStrategies.STRICT);
		
		
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		
		objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
	}

	/**
	 * Hide from public usage.
	 */
	private ObjectMapperUtils() {
	}

	/**
	 * <p>Note: outClass object must have default constructor with no arguments</p>
	 *
	 * @param <D>      type of result object.
	 * @param <T>      type of source object to map from.
	 * @param entity   entity that needs to be mapped.
	 * @param outClass class of result object.
	 * @return new object of <code>outClass</code> type.
	 */
	public static <D, T> D map(final T entity, Class<D> outClass) {
		return modelMapper.map(entity, outClass);
	}

	/**
	 * <p>Note: outClass object must have default constructor with no arguments</p>
	 *
	 * @param entityList list of entities that needs to be mapped
	 * @param outCLass   class of result list element
	 * @param <D>        type of objects in result list
	 * @param <T>        type of entity in <code>entityList</code>
	 * @return list of mapped object with <code><D></code> type.
	 */
	public static <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass) {
		return entityList.stream()
				.map(entity -> map(entity, outCLass))
				.collect(Collectors.toList());
	}

	/**
	 * Maps {@code source} to {@code destination}.
	 *
	 * @param source      object to map from
	 * @param destination object to map to
	 */
	public static <S, D> D map(final S source, D destination) {
		modelMapper.map(source, destination);
		return destination;
	}
	
	public static <D, T> D convertValue(final T entity, Class<D> outClass) {
	    Object valueToConvert = entity;

	    // If the source is a Map (e.g., cached config map), normalize date strings
	    if (entity instanceof Map) {
	        Map<?, ?> originalMap = (Map<?, ?>) entity;
	        Map<Object, Object> normalizedMap = new HashMap<>();
	        originalMap.forEach((key, val) -> normalizedMap.put(key, normalizeDateValue(val)));
	        valueToConvert = normalizedMap;
	    }

	    return objectMapper.convertValue(valueToConvert, outClass);
	}

	private static Object normalizeDateValue(Object val) {
		if (val instanceof String) {
			String str = (String) val;
			// Match dd-MM-yyyy pattern
			if (str.matches("\\d{2}-\\d{2}-\\d{4}")) {
				try {
					DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
					LocalDate date = LocalDate.parse(str, inputFormatter);

					// Return ISO string for LocalDateTime (start of day)
					return date.atStartOfDay().toString(); // yyyy-MM-ddTHH:mm:ss
				} catch (Exception e) {
					// ignore parse errors, leave original string
				}
			}
			return str;
		} else if (val instanceof Map) {
			Map<?, ?> map = (Map<?, ?>) val;
			Map<Object, Object> normalizedMap = new HashMap<>();
			map.forEach((k, v) -> normalizedMap.put(k, normalizeDateValue(v)));
			return normalizedMap;
		} else if (val instanceof Collection) {
			Collection<?> collection = (Collection<?>) val;
			return collection.stream()
					.map(ObjectMapperUtils::normalizeDateValue)
					.collect(Collectors.toList());
		} else {
			return val;
		}
	}
    
    public static String converJavaObjectToJsonString(Object object) {
        String json = "";
        try {
            json = objectWriter.writeValueAsString(object);
            logger.info("Converted Object to Json String --> " + json);
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            logger.error("Error converting object to JSON: ", e);
        }
        return json;
    }

}
