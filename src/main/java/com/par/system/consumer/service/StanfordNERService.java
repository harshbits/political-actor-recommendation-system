package com.par.system.consumer.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface StanfordNERService {
	
	public  List<String> getParsedSentence(String sentence);

}
