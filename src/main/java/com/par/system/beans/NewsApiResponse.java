package com.par.system.beans;

import java.io.Serializable;
import java.util.List;

public class NewsApiResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String status;

	private String source;

	private String sortBy;

	private List<NewsArticle> articles;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public List<NewsArticle> getArticles() {
		return articles;
	}

	public void setArticles(List<NewsArticle> articles) {
		this.articles = articles;
	}

}
