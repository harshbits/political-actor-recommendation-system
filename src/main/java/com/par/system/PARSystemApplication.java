package com.par.system;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.par.system.config.ApplicationProperties;
import com.par.system.consumer.service.ParConsumerService;
import com.par.system.producer.service.CameoDictionaryParseService;
import com.par.system.producer.service.ParScrapperService;

@SpringBootApplication
@ComponentScan
public class PARSystemApplication {

	private static final Logger logger = LoggerFactory.getLogger(PARSystemApplication.class);

	@Autowired
	private ApplicationProperties applicationProperties;
	
	@Autowired
	private ParScrapperService scrapperService;

	@Autowired
	private ParConsumerService consumerService;
	
	@Autowired
	private CameoDictionaryParseService cameoDictionaryParseService;

	public static void main(String[] args) {
		SpringApplication.run(PARSystemApplication.class, args);
	}

	@PostConstruct
	public void init() throws InterruptedException {
		
		//should add new actor from Cameo to Redis
		if(applicationProperties.isCameoToRedisAdd()){
			cameoDictionaryParseService.storeActors();	
		}
		
		try {
			// Run Scraper
			scrapperService.runScrapper();

			// Run Consumer
			consumerService.runConsumer();

		} catch (Exception e) {
			logger.error("{}", e);
		}
	}
}
