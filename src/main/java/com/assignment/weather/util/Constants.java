package com.assignment.weather.util;

public class Constants {
 
	private Constants() {
		throw new IllegalStateException("Utility class");
	}
	
	//Error messages
	public static final String PINCODE_EMPTY_MESSAGE = "pincode cannot be empty";
	public static final String DATE_EMPTY_MESSAGE = "date cannot be empty";
	public static final String PINCODE_AND_DATE_EMPTY_MESSAGE ="pincode and date cannot be empty";
	
	
	//apis
	public static final String GEO_API_GOOGLE ="/maps/api/geocode/json";
	public static final String OPEN_WEATHER_API ="/data/2.5/weather";
	
}
