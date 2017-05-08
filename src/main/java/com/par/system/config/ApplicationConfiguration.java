package com.par.system.config;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

import com.par.system.consumer.service.JaccardDistanceService;
import com.par.system.consumer.service.NewActorDiscoveryService;
import com.par.system.consumer.service.ParConsumerService;
import com.par.system.consumer.service.PerformNERJaccardService;
import com.par.system.consumer.service.StanfordNERService;
import com.par.system.consumer.service.impl.StanfordNERServiceImpl;
import com.par.system.producer.service.CameoDictionaryParseService;
import com.par.system.producer.service.NewsApiService;
import com.par.system.producer.service.ParScrapperService;
import com.par.system.producer.service.impl.NewsApiServiceImpl;
import com.par.system.repository.RedisMetadataManager;

@Configuration
public class ApplicationConfiguration{
	
//	@Autowired
//	private static RedisTemplate<String, String> redisTemplate;

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
	
	@Bean
	public CameoDictionaryParseService cameoDictionaryParseService(){
		return new CameoDictionaryParseService();
	}
	
	@Bean
	public NewActorDiscoveryService newActorDiscoveryService(){
		return new NewActorDiscoveryService();
	}
	
	@Bean
	public RedisMetadataManager redisMetadataManager(){
		return new RedisMetadataManager();
	}
	
//	@Bean
//	public static RedisConnection redisConnection(){
//		return redisTemplate.getConnectionFactory().getConnection();
//	}
//	

}
