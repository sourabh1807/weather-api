package com.assignment.weather.service.impl;

import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import com.assignment.weather.entity.WeatherData;
import com.assignment.weather.entity.WeatherDataIdentity;
import com.assignment.weather.repository.WeatherRespository;
import com.assignment.weather.service.WeatherService;
import com.assignment.weather.util.Constants;
import com.assignment.weather.util.WeatherUtil;
import com.assignment.weather.vo.GeoResponseVO;
import com.assignment.weather.vo.Location;
import com.assignment.weather.vo.WeatherResponseVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WeatherServiceImpl implements WeatherService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private WeatherRespository weatherRespository;
	
	@Value("${google.api.key}")
	private String googleKey;
	
	@Value("${google.api.endpoint}")
	private String googleApiEndpoint;
	
	@Value("${openweather.api.key}")
	private String openWeatherKey;
	
	@Value("${openweather.api.endpoint}")
	private String openWeatherApiEndpoint;
	
	@Value("${date.format}")
	private String dateFormat;

	@Override
	public WeatherResponseVO getWeatherData(String pincode,String date) {
		
		//call database for weather data
		Optional<List<WeatherData>> weatherOptional = weatherRespository.findDataByPincode(pincode);
		List<WeatherData> weatherDataList = null;
		if(weatherOptional.isPresent())
			weatherDataList = weatherOptional.get();
		
		WeatherResponseVO weatherResponseVO = null;
		float latitude = 0;
		float longitude = 0;
		boolean skipGeoApiCall = false;
		
		if(Objects.nonNull(weatherDataList)) {
			for(WeatherData wdata:weatherDataList) {
				if(wdata.getIdentity().getDate().equals(date)) {
					try {
						weatherResponseVO = new ObjectMapper().readValue(wdata.getData(), WeatherResponseVO.class);
						break;
					} catch (JsonProcessingException e) {
						e.printStackTrace();
					} 
				}
			}
			
			if(weatherResponseVO == null) {
				latitude = weatherDataList.get(0).getLatitute();
				longitude= weatherDataList.get(0).getLongitute();
				skipGeoApiCall = true;
			}
		}

		
		if(Objects.isNull(weatherResponseVO)) {
			
			if(!skipGeoApiCall) {
				GeoResponseVO responseVO = callGoogleApiForLocation(pincode);
				Location location = responseVO.getResults()[0].getGeometry().getLocation();
				latitude = location.getLat();
				longitude = location.getLng();
			}

			weatherResponseVO = callWeatherApi(latitude,longitude);
			
			if(weatherResponseVO != null) {
				ObjectMapper mapper = new ObjectMapper();
				String data =null;
				try {
					 data  = mapper.writeValueAsString(weatherResponseVO);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
				}
				
				// storing data in db
				WeatherData weatherData = new WeatherData();
				
				WeatherDataIdentity identity = new WeatherDataIdentity();
				identity.setPincode(pincode);
				identity.setDate(date);
				weatherData.setData(data);
				weatherData.setIdentity(identity);
				weatherData.setLatitute(latitude);
				weatherData.setLongitute(longitude);
				
				weatherRespository.save(weatherData);
			}
			
		}

		return weatherResponseVO;
		
	}
	
	public GeoResponseVO callGoogleApiForLocation(String pincode) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		
		String url = googleApiEndpoint+Constants.GEO_API_GOOGLE;
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
				.queryParam("address", pincode)
				.queryParam("key", googleKey);
		
		return  restTemplate.exchange(builder.buildAndExpand().toUri(), HttpMethod.GET, new HttpEntity(headers),
				GeoResponseVO.class).getBody();

		
	}
	
	public WeatherResponseVO callWeatherApi(float lat ,float lng) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		
		String url = openWeatherApiEndpoint+Constants.OPEN_WEATHER_API;
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
				.queryParam("lat", lat)
				.queryParam("lon", lng)
				.queryParam("appid", openWeatherKey);
		
		return restTemplate.exchange(builder.buildAndExpand().toUri(), HttpMethod.GET, new HttpEntity(headers),
				WeatherResponseVO.class).getBody();
	}
	
	
}
