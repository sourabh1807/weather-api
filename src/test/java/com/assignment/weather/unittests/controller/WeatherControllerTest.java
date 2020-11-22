package com.assignment.weather.unittests.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.assignment.weather.controller.WeatherController;
import com.assignment.weather.exception.InvalidInputException;
import com.assignment.weather.vo.WeatherResponseVO;

public class WeatherControllerTest {
	
	@InjectMocks
	private WeatherController weatherController;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getWeatherData_pincode_null_raiseException() {
		
          assertThrows(InvalidInputException.class, () -> weatherController.getWeatherData(null,"22-11-2020"));		
	}
	
	@Test
	public void getWeatherData_date_null_raiseException() {
		
          assertThrows(InvalidInputException.class, () -> weatherController.getWeatherData("411026",null));		
	}
	
	@Test
	public void getWeatherData_date_and_pincode_null_raiseException() {
		
          assertThrows(InvalidInputException.class, () -> weatherController.getWeatherData(null,null));		
	}
}
