package com.samyem.webblocks.client.pallet;

import java.util.function.Function;

import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.samyem.webblocks.client.WidgetAppObject;

public class TextProperty<W extends Widget> extends Property<String, W> {

	public TextProperty(String key, PropertyApplier<String, W> propApplier,
			Function<WidgetAppObject<W>, String> propInitializer, SetterGenerator setterGenerator,
			GetterGenerator getterGenerator) {
		super(key, propApplier, propInitializer, setterGenerator, getterGenerator);
	}

	@Override
	public Widget createValueEditor(WidgetAppObject<W> widget) {
		TextBox input = new TextBox();
		input.addBlurHandler(event -> propApplier.apply(widget, input.getValue()));

		input.setValue(propExtractor.apply(widget));
		return input;
	}

	@Override
	public String getStringValue() {
		return getValue();
	}

	@Override
	public void setStringValue(String value) {
		setValue(value);
	}

}
