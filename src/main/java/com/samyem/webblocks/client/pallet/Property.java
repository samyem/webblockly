package com.samyem.webblocks.client.pallet;

import com.google.gwt.user.client.ui.Widget;

public abstract class Property<T> {
	private String key;
	private String description;

	/**
	 * Make widget to input the value of the property
	 * 
	 * @return
	 */
	public abstract Widget getValueEditor();

	public abstract String getStringValue();

	public abstract void setStringValue(String value);

	public abstract T getValue();

	public abstract void setValue(T value);

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
