package com.codingvine.quartzjob.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class AppConfig {


	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);

//		SimpleModule module = new SimpleModule();
//		module.addSerializer(new Java8LocalDateStdSerializer());
//		module.addDeserializer(LocalDate.class, new Java8LocalDateStdDeserializer());

		mapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		//mapper.registerModule(module);
		
		 mapper.registerModule(new JavaTimeModule());
	        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		return mapper;
	}

}