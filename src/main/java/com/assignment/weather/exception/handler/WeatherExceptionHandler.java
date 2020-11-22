package com.assignment.weather.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.assignment.weather.exception.InvalidInputException;
import com.assignment.weather.exception.response.ErrorResponseVO;



@RestControllerAdvice
public class WeatherExceptionHandler {

	@ExceptionHandler(InvalidInputException.class)
	public ResponseEntity<ErrorResponseVO> handleInvalidException(InvalidInputException ex) {
		ErrorResponseVO error = new ErrorResponseVO(ex.getErrorMessage());
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}	
}
