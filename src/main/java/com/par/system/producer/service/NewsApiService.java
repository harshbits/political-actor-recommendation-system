package com.par.system.producer.service;

import java.util.List;

import com.par.system.beans.Results;

public interface NewsApiService {
	
	public List<Results> getNewsArticles();
}
