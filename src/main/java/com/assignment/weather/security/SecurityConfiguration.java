package com.assignment.weather.security;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Value("${users}")
	private String[] USERS;
	

	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		
		List<String[]> users = readUserConfigurations();
		
		for(String[] user:users) {
			auth.inMemoryAuthentication()
			.passwordEncoder(passwordEncoder())
			 .withUser(user[0]).
			 password(user[1]).
			 roles("USER");
			
		}
		
			
	}
	
	
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authReq = httpSecurity
				.httpBasic().and()
				.authorizeRequests();
		
		authReq.antMatchers("/getWeatherData/**").hasAnyRole("USER");

	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new CustomPasswordEncoder();
	}
	
	private List<String[]> readUserConfigurations() {
		List<String[]> returnValue = new ArrayList<>();
		for (String line : USERS) {
			if (line!="") {
				String[] tokens = line.split(":");
				returnValue.add(tokens);
			}
		}
		return returnValue;
	}
}
