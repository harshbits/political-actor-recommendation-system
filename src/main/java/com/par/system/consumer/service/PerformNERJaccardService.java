package com.par.system.consumer.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.par.system.beans.ActorGroupFrequency;
import com.par.system.beans.PoliticalNewsData;
import com.par.system.beans.Results;

public class PerformNERJaccardService implements Serializable {


	private static final long serialVersionUID = 1L;

	@Autowired
	private StanfordNERService nerService;

	@Autowired
	private JaccardDistanceService jaccardDistanceService;

	public ActorGroupFrequency getActorGroupFrequency(PoliticalNewsData newsData) {

		ActorGroupFrequency response = new ActorGroupFrequency();

		Map<String, Integer> actorFrequency = new HashMap<>();

		for (Results result : newsData.getResults()) {

			List<String> actorList = nerService.getParsedSentence(result.getContent() + result.getTitle());
			for (String actor : actorList) {
				if (actorFrequency.containsKey(actor)) {
					actorFrequency.put(actor,
							actorFrequency.get(actor) + countFrequency(result.getContent() + result.getTitle(), actor));
				} else {
					actorFrequency.put(actor, countFrequency(result.getContent() + result.getTitle(), actor));
				}
			}

		}
		response.setActorFrequency(actorFrequency);

		List<Map<String, Integer>> actorGroup = jaccardDistanceService.groupActors(actorFrequency);
		response.setActorGroup(actorGroup);

		Map<String, Integer> highestOccuredActorMap = calculateRank(actorGroup);
		response.setHighestOccuredActorMap(highestOccuredActorMap);
		
		return response;

	}

	/**
	 * 
	 * @param actorGroup
	 * @return
	 */
	private Map<String, Integer> calculateRank(List<Map<String, Integer>> actorGroup) {

		Map<String, Integer> highestOccuredActorMap = new HashMap<>();
		int max = 0;
		for (Map<String, Integer> actorMap : actorGroup) {
			int total = 0;
			for (String actor : actorMap.keySet()) {
				total += actorMap.get(actor);
			}
			if (max < total) {
				max = total;
				highestOccuredActorMap = actorMap;
			}
		}

		return highestOccuredActorMap;
	}

	/**
	 * count frequency of occurred actor in document
	 * 
	 * @param data
	 * @param actor
	 * @return
	 */
	private int countFrequency(String data, String actor) {

		return StringUtils.countMatches(data.toLowerCase(), actor.toLowerCase());
	}

}
