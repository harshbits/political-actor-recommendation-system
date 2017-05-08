package com.par.system.config;

import java.io.Serializable;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.properties")
public class ApplicationProperties implements Serializable{

	private static final long serialVersionUID = 1L;

	private String newsApiUrl;

	private String newsApiKey;

	private String newsApisource;

	private double jaccardDistanceThreshold;

	private boolean addNewActorAutomatic;

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

	public double getJaccardDistanceThreshold() {
		return jaccardDistanceThreshold;
	}

	public void setJaccardDistanceThreshold(double jaccardDistanceThreshold) {
		this.jaccardDistanceThreshold = jaccardDistanceThreshold;
	}

	public boolean isAddNewActorAutomatic() {
		return addNewActorAutomatic;
	}

	public void setAddNewActorAutomatic(boolean addNewActorAutomatic) {
		this.addNewActorAutomatic = addNewActorAutomatic;
	}

}
