package com.assignment.weather.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.assignment.weather.entity.WeatherData;
import com.assignment.weather.entity.WeatherDataIdentity;

@Repository
public interface WeatherRespository  extends JpaRepository<WeatherData,WeatherDataIdentity>{
	
	@Query("SELECT t FROM WeatherData t where t.identity.pincode = :pincode") 
	Optional<List<WeatherData>> findDataByPincode(@Param("pincode") String pincode);

}
