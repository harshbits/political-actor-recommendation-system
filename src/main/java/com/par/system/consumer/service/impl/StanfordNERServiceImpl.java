package com.par.system.consumer.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.par.system.beans.EngilshTagResponse;
import com.par.system.beans.NERType;
import com.par.system.consumer.service.StanfordNERService;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class StanfordNERServiceImpl implements StanfordNERService {

	private static Logger logger = LoggerFactory.getLogger(StanfordNERServiceImpl.class);

	@Override
	public EngilshTagResponse getParsedSentence(String data) {

		EngilshTagResponse response = new EngilshTagResponse();

		try {
			Properties props = new Properties();
			props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
			StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
			Annotation document = new Annotation(data);
			pipeline.annotate(document);

			List<CoreMap> sentences = document.get(SentencesAnnotation.class);

			Map<String, NERType> actorData = new HashMap<>();

			for (CoreMap sentence : sentences) {

				String previosCategory = "";
				String previosWord = "";
				for (CoreLabel token : sentence.get(TokensAnnotation.class)) {

					String word = token.word();

					String category = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);

					if (!"O".equals(category)) {

						if (previosCategory == category) {

							if (actorData.containsKey(previosWord)) {
								actorData.remove(previosWord);
								previosWord = previosWord + " " + word;
								actorData.put(previosWord, NERType.valueOf(category));
							}
						} else {
							actorData.put(word, NERType.valueOf(category));
						}
						previosCategory = category;
						previosWord = word;
					}
				}

			}

			logger.info(actorData.toString());

		} catch (Exception e) {
			logger.error("", e);
		}
		return response;
	}
}
