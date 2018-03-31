package com.samyem.webblocks.client.pallet;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextBox;
import com.samyem.webblocks.client.WidgetAppObject;
import com.samyem.webblocks.client.pallet.Property.GetterGenerator;
import com.samyem.webblocks.client.pallet.Property.PropertyApplier;
import com.samyem.webblocks.client.pallet.Property.SetterGenerator;

/**
 * Item that creates new text box
 * 
 * @author samyem
 *
 */
public class TextBoxPalletItem extends StyledPalletItem<TextBox> {
	public TextBoxPalletItem(Consumer<ComponentPalletItem<TextBox>> consumerOfThis, Supplier<Integer> docLeft,
			Supplier<Integer> docTop) {
		super(consumerOfThis, docLeft, docTop);

		// properties
		addValue();
	}

	private void addValue() {
		PropertyApplier<String, TextBox> propApplier = (w, value) -> ((HasValue) w.getWidget()).setValue(value);
		Function<WidgetAppObject<TextBox>, String> propInitializer = t -> ((HasValue) t.getWidget()).getValue()
				.toString();
		SetterGenerator setterProps = value -> "val(" + value + ")";
		GetterGenerator getterProps = () -> "val()";
		TextProperty<TextBox> captionProp = new TextProperty<>("Value", propApplier, propInitializer, setterProps,
				getterProps);
		addProperty(captionProp);
	}

	@Override
	public WidgetAppObject<TextBox> createAppObject() {
		TextBox widget = new TextBox();
		widget.addKeyUpHandler(event -> event.preventDefault());
		WidgetAppObject<TextBox> wAppObj = new WidgetAppObject<>(widget);
		return wAppObj;
	}

	@Override
	public String getKey() {
		return "textBox";
	}

	@Override
	public String getFriendlyName() {
		return "Text Input";
	}

}