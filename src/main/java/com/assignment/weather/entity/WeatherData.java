package com.assignment.weather.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="weather_data")
public class WeatherData {
	
	@EmbeddedId
	private WeatherDataIdentity identity;

	
	@Column(name = "wdata")
	private String data;
	private float latitute;
	private float longitute;
	
	

	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public float getLatitute() {
		return latitute;
	}
	public void setLatitute(float latitute) {
		this.latitute = latitute;
	}
	public float getLongitute() {
		return longitute;
	}
	public void setLongitute(float longitute) {
		this.longitute = longitute;
	}
	public WeatherDataIdentity getIdentity() {
		return identity;
	}
	public void setIdentity(WeatherDataIdentity identity) {
		this.identity = identity;
	}
	
	

	
}
