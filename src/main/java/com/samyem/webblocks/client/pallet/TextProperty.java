package com.samyem.webblocks.client.pallet;

import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.samyem.webblocks.client.WidgetAppObject;

public class TextProperty<W extends Widget> extends Property<String, W> {

	public TextProperty(PropertyApplier<String, W> propApplier) {
		super(propApplier);
	}

	@Override
	public Widget getValueEditor(WidgetAppObject<W> widget) {
		TextBox input = new TextBox();
		input.addBlurHandler(event -> propApplier.apply(widget, input.getValue()));
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
