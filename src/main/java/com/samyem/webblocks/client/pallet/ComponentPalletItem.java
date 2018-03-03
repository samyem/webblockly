package com.samyem.webblocks.client.pallet;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.Widget;
import com.samyem.webblocks.shared.AppObject;

/**
 * Base pallet item
 * 
 * @author samye
 *
 */
public abstract class ComponentPalletItem {
	protected Consumer<ComponentPalletItem> consumerOfThis;

	protected Supplier<Integer> docLeft, docTop;

	protected List<Property<?>> properties = new ArrayList<>();

	public ComponentPalletItem(Consumer<ComponentPalletItem> consumerOfThis, Supplier<Integer> docLeft,
			Supplier<Integer> docTop) {
		this.consumerOfThis = consumerOfThis;
		this.docTop = docTop;
		this.docLeft = docLeft;
	}

	public abstract String getKey();

	public abstract Widget createWidget();

	public AppObject createAppObject() {
		AppObject obj = new AppObject();
		obj.setCode(getKey());
		return obj;
	}

	public abstract void handleDocumentCanvasClick(ClickEvent event);

	public abstract List<Property<?>> getProperties();
}