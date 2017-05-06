package com.par.system.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.properties")
public class ApplicationProperties {

	private String newsApiUrl;

	private String newsApiKey;
	
	private String newsApisource;

	public String getNewsApiUrl() {
		return newsApiUrl;
	}

	public void setNewsApiUrl(String newsApiUrl) {
		this.newsApiUrl = newsApiUrl;
	}

	public String getNewsApiKey() {
		return newsApiKey;
	}

	public void setNewsApiKey(String newsApiKey) {
		this.newsApiKey = newsApiKey;
	}

	public String getNewsApisource() {
		return newsApisource;
	}

	public void setNewsApisource(String newsApisource) {
		this.newsApisource = newsApisource;
	}

}
