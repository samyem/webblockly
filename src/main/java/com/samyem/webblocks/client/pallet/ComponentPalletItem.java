package com.samyem.webblocks.client.pallet;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Widget;
import com.samyem.webblocks.client.WidgetAppObject;
import com.samyem.webblocks.client.pallet.Property.PropertyApplier;

/**
 * Base pallet item
 * 
 * @author samye
 *
 */
public abstract class ComponentPalletItem<W extends Widget> {
	protected Consumer<ComponentPalletItem<W>> consumerOfThis;

	protected Supplier<Integer> docLeft, docTop;

	protected List<Property<?, W>> properties = new ArrayList<>();

	public ComponentPalletItem(Consumer<ComponentPalletItem<W>> consumerOfThis, Supplier<Integer> docLeft,
			Supplier<Integer> docTop) {
		this.consumerOfThis = consumerOfThis;
		this.docTop = docTop;
		this.docLeft = docLeft;

		PropertyApplier<String, W> idApplier = (w, value) -> w.getWidget().getElement().setId(value);
		Function<WidgetAppObject<W>, String> propInitializer = t -> t.getWidget().getElement().getId();

		TextProperty<W> nameProp = new TextProperty<>(idApplier, propInitializer);
		nameProp.setKey("Name");
		properties.add(nameProp);
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