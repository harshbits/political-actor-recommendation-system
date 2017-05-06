package com.par.system.producer.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.par.system.beans.NYTimesResponse;
import com.par.system.beans.Results;
import com.par.system.config.ApplicationProperties;
import com.par.system.producer.service.NewsApiService;

public class NewsApiServiceImpl implements NewsApiService {

	private static final Logger logger = LoggerFactory.getLogger(NewsApiServiceImpl.class);

	@Autowired
	private ApplicationProperties properties;

	@Override
	public List<Results> getNewsArticles() {

		List<Results> response = new ArrayList<>();

//		String requestUrl = properties.getNewsApiUrl() + "?source=" + properties.getNewsApisource()
//				+ "&sortBy=latest&apiKey=" + properties.getNewsApiKey();
		
		String requestUrl = properties.getNewsApiUrl() + properties.getNewsApisource() + "/200.json";

		RestTemplate restTemplate = new RestTemplate();

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.set("api-key", properties.getNewsApiKey());
			headers.add("Accept", "*/*");
			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(headers);
			
			ResponseEntity<String> result = restTemplate.exchange(requestUrl, HttpMethod.GET, requestEntity,
					String.class);
			HttpStatus status = result.getStatusCode();
			logger.info("Response Status: " + status);
			Gson gson = new Gson();
			NYTimesResponse newsApiResponse = gson.fromJson(result.getBody(), NYTimesResponse.class);
//			NewsApiResponse newsApiResponse = gson.fromJson(result.getBody(), NewsApiResponse.class);

			if (result != null) {
				logger.info(result.getBody());
				if (newsApiResponse.getResults().size() > 0) {
					response = newsApiResponse.getResults();
				}
			}
		} catch (HttpClientErrorException e) {
			logger.error(e.getResponseBodyAsString());
		} catch (Exception e) {
			logger.error("Inside GET News API Exception: ", e);
		}
		return response;
	}

}
