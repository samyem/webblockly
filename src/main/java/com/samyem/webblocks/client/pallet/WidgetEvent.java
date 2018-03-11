package com.samyem.webblocks.client.pallet;

import com.google.gwt.user.client.ui.Widget;
import com.samyem.webblocks.client.WidgetAppObject;

/**
 * An event of a widget object
 * 
 * @author samye
 *
 */
public class WidgetEvent {
	private String key;
	private String description;

	public interface PropertyApplier<T, W extends Widget> {
		void apply(WidgetAppObject<W> widget, T value);
	}

	public interface SetterGenerator {
		String apply(String value);
	}

	public interface GetterGenerator {
		String apply();
	}

	/**
	 * The corresponding jquery event for code generation
	 */
	private String jQueryEventName;

	public WidgetEvent(String key, String jQueryEventName) {
		this.key = key;
		this.jQueryEventName = jQueryEventName;
	}

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

	public String getjQueryEventName() {
		return jQueryEventName;
	}

}
