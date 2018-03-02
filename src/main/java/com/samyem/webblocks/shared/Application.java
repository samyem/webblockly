package com.samyem.webblocks.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Application implements Serializable {
	private static final long serialVersionUID = -1772073813234307089L;

	private String name = "Unnamed Application";
	private String description = "";

	/**
	 * Top level objects in the application. Starts with the first object
	 */
	private List<AppObject> objects = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<AppObject> getObjects() {
		return objects;
	}

}
