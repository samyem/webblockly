package com.samyem.webblocks.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An object within application.
 * 
 * @author samyem
 *
 */
public class AppObject implements Serializable {
	private static final long serialVersionUID = 1128804156574835523L;

	/**
	 * The type of item
	 */
	private String itemTypeKey;

	private String name;

	/**
	 * Key value. Value needs to be encoded into a string
	 */
	private Map<String, String> properties = new HashMap<>();

	private List<AppObject> items = new ArrayList<>();

	private String code = "";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<AppObject> getItems() {
		return items;
	}

	public void setItems(List<AppObject> items) {
		this.items = items;
	}

	public Map<String, String> getProperties() {
		return properties;
	}

	public String getItemTypeKey() {
		return itemTypeKey;
	}

	public void setItemTypeKey(String itemTypeKey) {
		this.itemTypeKey = itemTypeKey;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
