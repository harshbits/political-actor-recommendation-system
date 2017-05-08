package com.par.system.consumer.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.par.system.config.ApplicationProperties;

public class JaccardDistanceService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	private ApplicationProperties applicationProperties;

//	public List<List<String>> groupActors(List<String> actors) {
	public List<Map<String, Integer>> groupActors(Map<String, Integer> actorFrequency) {

		List<String> actors = actorFrequency
				.entrySet().stream()
				.map(p -> p.getKey())
				.collect(Collectors.toList());
		
		List<Map<String, Integer>> result = new ArrayList<>();

		double[][] matrix = new double[actors.size()][actors.size()];

		for (int i = 0; i < actors.size(); i++) {
			for (int j = 0; j < actors.size(); j++) {
				matrix[i][j] = calcJacardDistance(actors.get(i), actors.get(j));
			}
		}

		List<String> list = new ArrayList<>();
		HashSet<String> set = new HashSet<>();
		for (int i = 0; i < actors.size(); i++) {
			String s = "";
			for (int j = 0; j < actors.size(); j++) {

				if (matrix[i][j] < applicationProperties.getJaccardDistanceThreshold()) {
					if (s.trim().length() > 0) {
						s = s + "," + actors.get(j);
					} else {
						s = actors.get(j);
					}
				}
			}
			set.add(s);
			list.add(s.trim());
		}

		for (String combinedActors : set) {
			String[] actorsArray = combinedActors.trim().split(",");
			Map<String, Integer> actorMap = new HashMap<>();
			for(String actor: actorsArray){
				actorMap.put(actor, actorFrequency.get(actor));
			}
			result.add(actorMap);
		}
		return result;
	}

	/**
	 * Calculate Jaccard Distance Between 2 Actors
	 * 
	 * @param a1
	 * @param a2
	 * @return
	 */
	private double calcJacardDistance(String a1, String a2) {

		List<String> X = Arrays.asList(a1.toLowerCase().replaceAll("[.!:,'\\''/']", "").split(" "));
		List<String> Y = Arrays.asList(a2.toLowerCase().replaceAll("[.!:,'\\''/']", "").split(" "));

		Set<String> unionXY = new HashSet<String>(X);
		unionXY.addAll(Y);

		Set<String> intersectionXY = new HashSet<String>(X);
		intersectionXY.retainAll(Y);

		return (double) (1 - (intersectionXY.size() / (double) unionXY.size()));

	}

}
