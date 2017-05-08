package com.par.system.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ActorGroupFrequency implements Serializable {

	private static final long serialVersionUID = 1L;

	private Map<String, Integer> actorFrequency;

	private List<Map<String, Integer>> actorGroup;

	private Map<String, Integer> highestOccuredActorMap;

	public Map<String, Integer> getActorFrequency() {
		return actorFrequency;
	}

	public void setActorFrequency(Map<String, Integer> actorFrequency) {
		this.actorFrequency = actorFrequency;
	}

	public List<Map<String, Integer>> getActorGroup() {
		return actorGroup;
	}

	public void setActorGroup(List<Map<String, Integer>> actorGroup) {
		this.actorGroup = actorGroup;
	}

	public Map<String, Integer> getHighestOccuredActorMap() {
		return highestOccuredActorMap;
	}

	public void setHighestOccuredActorMap(Map<String, Integer> highestOccuredActorMap) {
		this.highestOccuredActorMap = highestOccuredActorMap;
	}

}
