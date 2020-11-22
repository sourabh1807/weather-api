package com.assignment.weather.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.weather.exception.InvalidInputException;
import com.assignment.weather.service.WeatherService;
import com.assignment.weather.util.Constants;
import com.assignment.weather.vo.WeatherResponseVO;

@RestController
public class WeatherController {
	
	@Autowired
	private WeatherService weatherService;

	@GetMapping("/getWeatherData/{pincode}/{date}")
	public WeatherResponseVO getWeatherData(@PathVariable String pincode,@PathVariable String date) throws InvalidInputException {
		
		if(pincode == null && date == null)
			throw new InvalidInputException(Constants.PINCODE_AND_DATE_EMPTY_MESSAGE);
		if(pincode == null)
			throw new InvalidInputException(Constants.PINCODE_EMPTY_MESSAGE);
		if(date == null)
			throw new InvalidInputException(Constants.DATE_EMPTY_MESSAGE);
		
		
		return weatherService.getWeatherData(pincode, date);
		
	}
}
