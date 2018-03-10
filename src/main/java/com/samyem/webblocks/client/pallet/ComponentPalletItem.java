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

	public ComponentPalletItem(Consumer<ComponentPalletItem<W>> consumerOfThis, Supplier<Integer> docLeft,
			Supplier<Integer> docTop) {
		this.consumerOfThis = consumerOfThis;
		this.docTop = docTop;
		this.docLeft = docLeft;

		PropertyApplier<String, W> idApplier = (w, value) -> w.getWidget().getElement().setId(value);
		Function<WidgetAppObject<W>, String> propInitializer = t -> t.getWidget().getElement().getId();

		TextProperty<W> nameProp = new TextProperty<>("Name", idApplier, propInitializer, null);
		addProperty(nameProp);
	}

	protected void addStyleProp(String property, Function<Style, String> styleGetter,
			BiConsumer<Style, String> styleSetter, String styleName) {
		Function<WidgetAppObject<W>, String> propInitializer = t -> styleGetter
				.apply(t.getWidget().getElement().getStyle());
		PropertyApplier<String, W> propApplier = (w, value) -> styleSetter.accept(w.getWidget().getElement().getStyle(),
				value);

		SetterGenerator setterProps = value -> "css('" + styleName + "'," + value + ")";
		TextProperty<W> prop = new TextProperty<>(property, propApplier, propInitializer, setterProps);
		addProperty(prop);
	}

	protected void addProperty(Property<?, W> prop) {
		properties.add(prop);
		propMap.put(prop.getKey(), prop);
	}

	public String generatePropertySetter(String property, String value) throws Exception {
		if (property == null || property.isEmpty()) {
			throw new RuntimeException("No property was selected");
		}

		Property<?, W> setterGen = propMap.get(property);
		if (setterGen == null) {
			GWT.log("Available props are: " + propMap.keySet().toString());
			throw new Exception("Invalid property " + property);
		}
		SetterGenerator setterGenerator = setterGen.getSetterGenerator();
		if (setterGenerator == null) {
			return ";\n";
		}
		return setterGenerator.apply(value);
	}

	/**
	 * Key to identify this type of item
	 * 
	 * @return
	 */
	public abstract String getKey();

	/**
	 * An app object that has a widget attached to it
	 * 
	 * @return
	 */
	public abstract WidgetAppObject<? extends Widget> createAppObject();

	public abstract void handleDocumentCanvasClick(ClickEvent event);

	public List<Property<?, W>> getProperties() {
		return properties;
	}

}