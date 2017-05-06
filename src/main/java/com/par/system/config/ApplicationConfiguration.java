package com.par.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.par.system.producer.service.NewsApiService;
import com.par.system.producer.service.ParScrapperService;
import com.par.system.producer.service.impl.NewsApiServiceImpl;

@Configuration
public class ApplicationConfiguration {

	@Bean
	public ParScrapperService scrapperService() {
		return new ParScrapperService();
	}

	@Bean
	public NewsApiService newsApiService() {
		return new NewsApiServiceImpl();
	}

}
