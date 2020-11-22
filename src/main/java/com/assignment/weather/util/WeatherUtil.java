package com.assignment.weather.util;


import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class WeatherUtil {
	
	private WeatherUtil() {
		throw new IllegalStateException("Utility class");
	}
	
	public static Date convertStringToSqlDate(String dateString, String format) {
		
		SimpleDateFormat sf = new SimpleDateFormat(format);
		java.util.Date date = null;
		try {
			date= sf.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		return new java.sql.Date(date.getTime());
	}
	
   public static String convertSqlDateToString(Date date, String format) {
		
		SimpleDateFormat sf = new SimpleDateFormat(format);

		return sf.format(date);
	}	

}
