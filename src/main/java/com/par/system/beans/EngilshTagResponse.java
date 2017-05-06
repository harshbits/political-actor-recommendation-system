package com.par.system.beans;

import java.io.Serializable;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class EngilshTagResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String inputSentence;

	private List<WordTagging> tagWords;

	private HttpStatus httpStatus;

	private ErrorObject errorObject;

	public String getInputSentence() {
		return inputSentence;
	}

	public void setInputSentence(String inputSentence) {
		this.inputSentence = inputSentence;
	}

	public List<WordTagging> getTagWords() {
		return tagWords;
	}

	public void setTagWords(List<WordTagging> tagWords) {
		this.tagWords = tagWords;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public ErrorObject getErrorObject() {
		return errorObject;
	}

	public void setErrorObject(ErrorObject errorObject) {
		this.errorObject = errorObject;
	}

}
