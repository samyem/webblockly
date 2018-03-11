package com.samyem.webblocks.client.pallet;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.samyem.webblocks.client.WidgetAppObject;
import com.samyem.webblocks.client.pallet.Property.GetterGenerator;
import com.samyem.webblocks.client.pallet.Property.PropertyApplier;
import com.samyem.webblocks.client.pallet.Property.SetterGenerator;

/**
 * Item with text on it
 * 
 * @author samye
 *
 */
public abstract class TextualPalletItem<W extends Widget> extends StyledPalletItem<W> {

	public TextualPalletItem(Consumer<ComponentPalletItem<W>> consumerOfThis, Supplier<Integer> docLeft,
			Supplier<Integer> docTop) {
		super(consumerOfThis, docLeft, docTop);

		addText();
	}

	private void addText() {
		PropertyApplier<String, W> propApplier = (w, value) -> ((HasText) w.getWidget()).setText(value);
		Function<WidgetAppObject<W>, String> propInitializer = t -> ((HasText) t.getWidget()).getText();
		SetterGenerator setterProps = value -> "text(" + value + ")";
		GetterGenerator getterProps = () -> "text()";
		TextProperty<W> captionProp = new TextProperty<>("Text", propApplier, propInitializer, setterProps,
				getterProps);
		addProperty(captionProp);
	}

}
