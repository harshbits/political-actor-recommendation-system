package com.par.system.producer.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.par.system.beans.Results;
import com.par.system.config.KafkaAppProperties;

@Component
public class ParScrapperService {

	private static final Logger logger = LoggerFactory.getLogger(ParScrapperService.class);

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	private KafkaAppProperties kafkaProperties;

	@Autowired
	private NewsApiService newsApiService;

	@Scheduled(fixedRate = 60000)
	public void runScrapper() {

		//collect all news articles
		List<Results> articles = newsApiService.getNewsArticles();
		
		if (articles.size() > 0) {
			articles = filterPoliticalData(articles);
			String dataToSend = new Gson().toJson(articles);
			send(kafkaProperties.getTopic(), dataToSend);
			logger.info(dataToSend);
		}
	}
	
	/**
	 * Filter Political Entity data only
	 * 
	 * @param articles
	 * @return
	 */
	private List<Results> filterPoliticalData(List<Results> articles) {
		
		List<Results> result = articles.stream().filter(
				article -> article.getSubsection().equals("Politics")).collect(Collectors.toList());
		return result;
	}
	
	/**
	 * Push data to Kafka
	 * 
	 * @param topic
	 * @param data
	 */
	private void send(String topic, String data) {
		
		logger.info("sending data='{}' to topic='{}'", data, topic);
		kafkaTemplate.send(topic, data, data);
	}
}
