package com.assignment.weather.exception.response;

import java.time.LocalDateTime;

public class ErrorResponseVO {

	private String message;
	private String timestamp;
	
	private ErrorResponseVO() {
		timestamp = LocalDateTime.now().toString();
	}
	
	public ErrorResponseVO(String message) {
		this();
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	
}
