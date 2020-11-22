package com.assignment.weather.service;

import com.assignment.weather.vo.WeatherResponseVO;

public interface WeatherService {

	WeatherResponseVO getWeatherData(String pincode,String date);

}
