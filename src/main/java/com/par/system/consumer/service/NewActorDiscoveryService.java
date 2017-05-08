package com.par.system.consumer.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.par.system.beans.ActorGroupFrequency;
import com.par.system.repository.RedisMetadataManager;

public class NewActorDiscoveryService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(NewActorDiscoveryService.class);
	
	@Autowired
	private RedisMetadataManager redisMetadataManager;
	
	public HashMap<String, Integer> getNewActor(ActorGroupFrequency actorGroup) {

		Map<String, Boolean> validatedMap = actorGroup.getHighestOccuredActorMap().entrySet().stream()
				.collect(Collectors.toMap(e -> e.getKey(), e -> validateActor(formatActor(e.getKey()))));
		
		logger.info("\n\n\n"+new Gson().toJson(validatedMap));

		return null;
	}
	
	private String formatActor(String actor){
		String result = actor.trim().replace(" ", "_");
		result = result.toUpperCase().trim();		
		return result;
	}
	
	private boolean validateActor(String actor){
		return redisMetadataManager.validate(actor);
	}
	
	private void saveNewActorEntries(){
		
	}

}
