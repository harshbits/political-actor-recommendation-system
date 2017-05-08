package com.par.system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.par.system.consumer.service.JaccardDistanceService;
import com.par.system.consumer.service.ParConsumerService;
import com.par.system.consumer.service.PerformNERJaccardService;
import com.par.system.consumer.service.StanfordNERService;
import com.par.system.consumer.service.impl.StanfordNERServiceImpl;
import com.par.system.producer.service.CameoDictionaryParseService;
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

	@Bean
	public ParConsumerService consumerService() {
		return new ParConsumerService();
	}

	@Bean
	public StanfordNERService nerService() {
		return new StanfordNERServiceImpl();
	}

	@Bean
	public CameoDictionaryParseService cameoActorParseService() {
		return new CameoDictionaryParseService();
	}

	@Bean
	public JaccardDistanceService jaccardDistanceService() {
		return new JaccardDistanceService();
	}

	@Bean
	public PerformNERJaccardService performNERJaccardService() {
		return new PerformNERJaccardService();
	}

}
