package com.par.system.beans;

import java.io.Serializable;

public class CameoActor implements Serializable {

	private static final long serialVersionUID = 1L;

	private Actor actor;

	private String roleString;

	public Actor getActor() {
		return actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}

	public String getRoleString() {
		return roleString;
	}

	public void setRoleString(String roleString) {
		this.roleString = roleString;
	}

}
