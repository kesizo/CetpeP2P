package com.kesizo.cetpe.front.util.helper;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class DateConverterHelper {
	
	public static LocalDateTime convertToLocalDateTimeViaInstant(java.util.Date dateToConvert) {
	    return dateToConvert.toInstant()
	      .atZone(ZoneId.systemDefault())
	      .toLocalDateTime();
	}
	 
	public static LocalDateTime convertToLocalDateTimeViaMilisecond(java.util.Date dateToConvert) {
	    return Instant.ofEpochMilli(dateToConvert.getTime())
	      .atZone(ZoneId.systemDefault())
	      .toLocalDateTime();
	}
	
	public static java.sql.Date convertToDateViaSqlDate(LocalDate dateToConvert) {
	    return java.sql.Date.valueOf(dateToConvert);
	}
	
	public static java.util.Date convertToDateViaInstant(LocalDate dateToConvert) {
	    return java.util.Date.from(dateToConvert.atStartOfDay()
	      .atZone(ZoneId.systemDefault())
	      .toInstant());
	}
	
	public static java.util.Date convertToDateViaSqlTimestamp(LocalDateTime dateToConvert) {
	    return java.sql.Timestamp.valueOf(dateToConvert);
	}
	
	public static java.util.Date convertToDateViaInstant(LocalDateTime dateToConvert) {
	    return java.util.Date
	      .from(dateToConvert.atZone(ZoneId.systemDefault())
	      .toInstant());
	}
	
	public static LocalDateTime convertToLocalDateTime(java.util.Date dateToConvert) {
		return LocalDateTime.ofInstant(
			      dateToConvert.toInstant(), ZoneId.systemDefault());
	}
	
}
