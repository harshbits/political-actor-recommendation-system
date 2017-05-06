package com.par.system.beans;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PoliticalNewsData implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private List<Results> results;
	
	private Date timestamp = Calendar.getInstance().getTime();

	public List<Results> getResults() {
		return results;
	}

	public void setResults(List<Results> results) {
		this.results = results;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	

}
