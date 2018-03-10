package com.samyem.webblocks.client.pallet;

import java.util.function.Function;

import com.google.gwt.user.client.ui.Widget;
import com.samyem.webblocks.client.WidgetAppObject;

/**
 * A property of an object
 * 
 * @author samye
 *
 * @param <T>
 * @param <W>
 */
public abstract class Property<T, W extends Widget> {
	private String key;
	private String description;
	private T value;

	public interface PropertyApplier<T, W extends Widget> {
		void apply(WidgetAppObject<W> widget, T value);
	}

	public interface SetterGenerator {
		String apply(String value);
	}

	/**
	 * Set the widget with the property
	 */
	protected PropertyApplier<T, W> propApplier;

	/**
	 * Initialize property with the given widget
	 */
	protected Function<WidgetAppObject<W>, T> propExtractor;
	private SetterGenerator setterGenerator;

	public Property(String key, PropertyApplier<T, W> propApplier, Function<WidgetAppObject<W>, T> propInitializer,
			SetterGenerator setterGenerator) {
		this.key = key;
		this.propApplier = propApplier;
		this.propExtractor = propInitializer;

		this.setterGenerator = setterGenerator;
	}

	/**
	 * Make widget to input the value of the property
	 * 
	 * @return
	 */
	public abstract Widget createValueEditor(WidgetAppObject<W> widget);

	public abstract String getStringValue();

	public abstract void setStringValue(String value);

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

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public SetterGenerator getSetterGenerator() {
		return setterGenerator;
	}

}
