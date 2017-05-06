package com.par.system.consumer.service;

import org.springframework.stereotype.Service;

import com.par.system.beans.EngilshTagResponse;

@Service
public interface StanfordNERService {
	
	public EngilshTagResponse getParsedSentence(String sentence);

}
