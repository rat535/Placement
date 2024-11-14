package com.jobs.bitlabs.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration  
public class Swaggerconfig {

	@Bean 
	public OpenAPI  myCustome() {
		return new OpenAPI().info(new Info().title("BIT Labs").description("By Ratnesh"));
	}
}
