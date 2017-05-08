package com.par.system.beans;

import java.io.Serializable;
import java.util.List;

public class Actor implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	private List<String> alias;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getAlias() {
		return alias;
	}

	public void setAlias(List<String> alias) {
		this.alias = alias;
	}

}
