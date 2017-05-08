package com.par.system.consumer.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.par.system.consumer.service.StanfordNERService;

import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class StanfordNERServiceImpl implements StanfordNERService, Serializable {

	private static final long serialVersionUID = 1L;

	private static Logger logger = LoggerFactory.getLogger(StanfordNERServiceImpl.class);

	@Override
	public List<String> getParsedSentence(String data) {

		List<String> response = new ArrayList<>();

		try {
			Properties props = new Properties();
			props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
			StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
			Annotation document = new Annotation(data);
			pipeline.annotate(document);

			List<CoreMap> sentences = document.get(SentencesAnnotation.class);

			// Map<String, NERType> actorData = new HashMap<>();
			Map<String, String> actorData = new HashMap<>();

			for (CoreMap sentence : sentences) {

				String previosCategory = "";
				String previosWord = "";
				for (CoreLabel token : sentence.get(TokensAnnotation.class)) {

					String word = token.word();

					String category = token.get(NamedEntityTagAnnotation.class);

					if (!"O".equals(category)) {

						if (previosCategory == category) {
							if (actorData.containsKey(previosWord)) {
								actorData.remove(previosWord);
								previosWord = previosWord + " " + word;
								previosCategory = category;
								actorData.put(previosWord, category);
							}
						} else {
							actorData.put(word, category);
							previosCategory = category;
							previosWord = word;
						}
					}
				}
			}

			logger.info(actorData.toString());

//			response = actorData.entrySet().stream().filter(
//					p -> p.getValue().equalsIgnoreCase("PERSON") || p.getValue().equalsIgnoreCase("ORGANIZATION"))
//					.map(p -> p.getKey()).collect(Collectors.toList());
			response = actorData.entrySet().stream().filter(
					p -> p.getValue().equalsIgnoreCase("PERSON"))
					.map(p -> p.getKey()).collect(Collectors.toList());

		} catch (Exception e) {
			logger.error("", e);
		}
		return response;
	}
}
