package com.samyem.webblocks.client.pallet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Widget;
import com.samyem.webblocks.client.WidgetAppObject;
import com.samyem.webblocks.client.pallet.Property.GetterGenerator;
import com.samyem.webblocks.client.pallet.Property.PropertyApplier;
import com.samyem.webblocks.client.pallet.Property.SetterGenerator;

/**
 * Base pallet item
 * 
 * @author samye
 *
 */
public abstract class ComponentPalletItem<W extends Widget> {
	protected Consumer<ComponentPalletItem<W>> consumerOfThis;

	protected Supplier<Integer> docLeft, docTop;

	// Don't access these directly - always use addProperty
	private final List<Property<?, W>> properties = new ArrayList<>();
	private Map<String, Property<?, W>> propMap = new HashMap<>();

	private final List<WidgetEvent> events = new ArrayList<>();
	private Map<String, WidgetEvent> eventMap = new HashMap<>();

	public ComponentPalletItem(Consumer<ComponentPalletItem<W>> consumerOfThis, Supplier<Integer> docLeft,
			Supplier<Integer> docTop) {
		this.consumerOfThis = consumerOfThis;
		this.docTop = docTop;
		this.docLeft = docLeft;

		// base properties
		PropertyApplier<String, W> idApplier = (w, value) -> w.getWidget().getElement().setId(value);
		Function<WidgetAppObject<W>, String> propInitializer = t -> t.getWidget().getElement().getId();

		TextProperty<W> nameProp = new TextProperty<>("Name", idApplier, propInitializer, null, null);
		addProperty(nameProp);

		// base events
		addEvent(new WidgetEvent("clicked", "click"));
	}

	protected void addStyleProp(String property, Function<Style, String> styleGetter,
			BiConsumer<Style, String> styleSetter, String styleName) {
		Function<WidgetAppObject<W>, String> propInitializer = t -> styleGetter
				.apply(t.getWidget().getElement().getStyle());
		PropertyApplier<String, W> propApplier = (w, value) -> styleSetter.accept(w.getWidget().getElement().getStyle(),
				value);

		SetterGenerator setterProps = value -> "css('" + styleName + "'," + value + ")";
		GetterGenerator getterProps = () -> "css('" + styleName + "')";
		TextProperty<W> prop = new TextProperty<>(property, propApplier, propInitializer, setterProps, getterProps);
		addProperty(prop);
	}

	protected void addProperty(Property<?, W> prop) {
		properties.add(prop);
		propMap.put(prop.getKey(), prop);
	}

	protected void addEvent(WidgetEvent event) {
		events.add(event);
		eventMap.put(event.getKey(), event);
	}

	public String generatePropertyGetter(String property) throws Exception {
		Property<?, W> prop = getProperty(property);
		GetterGenerator getterGenerator = prop.getGetterGenerator();
		if (getterGenerator == null) {
			return ";\n";
		}
		return getterGenerator.apply();
	}

	public String generateEventName(String event) throws Exception {
		WidgetEvent wEvent = getEvent(event);
		return wEvent.getjQueryEventName();
	}

	public String generatePropertySetter(String property, String value) throws Exception {
		Property<?, W> prop = getProperty(property);
		SetterGenerator setterGenerator = prop.getSetterGenerator();
		if (setterGenerator == null) {
			return ";\n";
		}
		return setterGenerator.apply(value);
	}

	private Property<?, W> getProperty(String property) throws Exception {
		if (property == null || property.isEmpty()) {
			throw new RuntimeException("No property was selected");
		}

		Property<?, W> setterGen = propMap.get(property);
		if (setterGen == null) {
			GWT.log("Available props are: " + propMap.keySet().toString());
			throw new Exception("Invalid property " + property);
		}
		return setterGen;
	}

	private WidgetEvent getEvent(String event) throws Exception {
		if (event == null || event.isEmpty()) {
			throw new RuntimeException("No event was selected");
		}

		WidgetEvent wEvent = eventMap.get(event);
		if (wEvent == null) {
			GWT.log("Available events are: " + eventMap.keySet().toString());
			throw new Exception("Invalid event " + event);
		}
		return wEvent;
	}

	/**
	 * Key to identify this type of item
	 * 
	 * @return
	 */
	public abstract String getKey();

	public abstract String getFriendlyName();

	/**
	 * An app object that has a widget attached to it
	 * 
	 * @return
	 */
	public abstract WidgetAppObject<? extends Widget> createAppObject();

	/**
	 * Handle user clicking outside the widget when focus gets lost from this widget
	 * 
	 * @param event
	 */
	public void handleDocumentCanvasClick(ClickEvent event) {
		// Sub classes can implement
	}

	public List<Property<?, W>> getProperties() {
		return properties;
	}

	public List<WidgetEvent> getEvents() {
		return events;
	}

}